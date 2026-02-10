package com.customersu.dashapi.cases.pfs;

import com.customersu.dashapi.cases.enderecos.EnderecoDtoResponse;
import com.customersu.dashapi.cases.enderecos.EnderecoEntity;
import com.customersu.dashapi.cases.pessoas.PessoaDtoResponse;
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
public class PfService {

    private final PfRepository pfRepository;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    private PfDtoResponse toDtoResponse(PfEntity entity) {
        return PfDtoResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .ativo(entity.getAtivo())
                .endereco(new EnderecoDtoResponse(entity.getEndereco()))
                //específico PF:
                .cpf(entity.getCpf())
                .rg(entity.getRg())
                .nascimento(entity.getNascimento())
                .sexo(entity.getSexo())
                .nomeSocial(entity.getNomeSocial())
                .artigoPreferencia(entity.getArtigoPreferencia())

                .build();
    }


//  DtoRequest para Entity:
    private PfEntity toEntity(PfDtoRequest request) {
        return PfEntity.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .ativo(request.getAtivo())
                .endereco(new EnderecoEntity(request.getEndereco()))
                //específico PF:
                .cpf(request.getCpf())
                .rg(request.getRg())
                .nascimento(request.getNascimento())
                .sexo(request.getSexo())
                .nomeSocial(request.getNomeSocial())
                .artigoPreferencia(request.getArtigoPreferencia())

                .build();
    }

    private void updateEntity(PfEntity entity, PfDtoRequest request) {
        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone());
        entity.setAtivo(request.getAtivo());
        entity.setEndereco(new EnderecoEntity(request.getEndereco()));
        // específicos PF
        entity.setCpf(request.getCpf());
        entity.setRg(request.getRg());
        entity.setNascimento(request.getNascimento());
        entity.setSexo(request.getSexo());
        entity.setNomeSocial(request.getNomeSocial());
        entity.setArtigoPreferencia(request.getArtigoPreferencia());
    }

// CRUD:
//  C - CREATE
    public PfDtoResponse criar(PfDtoRequest request) {

        if (pfRepository.existsByCpf(request.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        PfEntity entity = toEntity(request);
        PfEntity salvo = pfRepository.save(entity);

        return toDtoResponse(salvo);
    }

//  R - READ (por ID e todos)
    public PfDtoResponse buscarPorId(Long id) {
        PfEntity entity = pfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public List<PfDtoResponse> listarTodos() {
        return pfRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<PfDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return pfRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public PfDtoResponse atualizar(Long id, PfDtoRequest request) {

        PfEntity entity = pfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        updateEntity(entity, request);

        PfEntity atualizado = pfRepository.save(entity);
        return toDtoResponse(atualizado);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!pfRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        pfRepository.deleteById(id);
    }

}
