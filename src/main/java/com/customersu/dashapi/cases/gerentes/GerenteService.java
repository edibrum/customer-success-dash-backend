package com.customersu.dashapi.cases.gerentes;

import com.customersu.dashapi.cases.pessoas.PessoaDtoResponse;
import com.customersu.dashapi.cases.pessoas.PessoaEntity;
import com.customersu.dashapi.cases.pessoas.PessoaService;
import com.customersu.dashapi.cases.pfs.PfDtoRequest;
import com.customersu.dashapi.cases.pfs.PfEntity;
import com.customersu.dashapi.cases.pfs.PfService;
import com.customersu.dashapi.cases.pjs.PjDtoRequest;
import com.customersu.dashapi.cases.pjs.PjEntity;
import com.customersu.dashapi.cases.pjs.PjService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GerenteService {

    private final GerenteRepository gerenteRepository;

    private final PessoaService pessoaService;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public GerenteDtoResponse toDtoResponse(GerenteEntity entity) {
        return GerenteDtoResponse.builder()
                .id(entity.getId())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .admissao(entity.getAdmissao())
                .pessoa(pessoaService.toDtoResponsePoliformPfPj(entity.getPessoa()))

                .build();
    }

// CRUD:
//  C - CREATE
    public GerenteDtoResponse criar(GerenteDtoRequest dto) {

        if (gerenteRepository.existsByPessoaId(dto.getPessoa().getId())) {
            throw new RuntimeException("Gerente já cadastrado com este usuário.");
        }

        PessoaEntity registroPessoa = pessoaService.criarOuAtualizarRegistroPessoa(dto.getPessoa());

        GerenteEntity entity = GerenteEntity.builder()
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .admissao(dto.getAdmissao())
                .pessoa(registroPessoa)

                .build();

        GerenteEntity salvo = gerenteRepository.save(entity);

        return toDtoResponse(salvo);
    }

//  R - READ (por ID e todos)
    public GerenteDtoResponse buscarPorId(Long id) {
        GerenteEntity entity = gerenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro (Gerente) não encontrado."));
        return toDtoResponse(entity);
    }

    public GerenteEntity buscarEntityPorId(Long id) {
        return gerenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro (Gerente) não encontrado."));
    }

    public GerenteDtoResponse buscarPorPessoaId(Long pessoaId) {
        GerenteEntity entity = gerenteRepository.findByPessoaId(pessoaId)
                .orElseThrow(() -> new RuntimeException("Registro (Gerente) não encontrado."));
        return toDtoResponse(entity);
    }

    public List<GerenteDtoResponse> listarTodos() {
        return gerenteRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<GerenteDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return gerenteRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public GerenteDtoResponse atualizar(Long pessoaId, GerenteDtoRequest dto) {
        GerenteEntity entity = gerenteRepository.findByPessoaId(pessoaId)
                .orElseThrow(() -> new RuntimeException("Registro (Gerente) não encontrado."));

        entity.setAtivo(dto.getAtivo());
        entity.setAdmissao(dto.getAdmissao());

            //Garantir atualizar dados da Pessoa (física ou jurídica)
            pessoaService.atualizar(pessoaId, dto.getPessoa());
            entity.setPessoa(pessoaService.buscarEntityPorId(pessoaId));

        GerenteEntity atualizada = gerenteRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletarPorId(Long gerenteId) {
        if (!gerenteRepository.existsById(gerenteId)) {
            throw new RuntimeException("Registro (Gerente) não encontrado.");
        }
        gerenteRepository.deleteById(gerenteId);
    }

}
