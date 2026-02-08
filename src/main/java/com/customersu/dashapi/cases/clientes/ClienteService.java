package com.customersu.dashapi.cases.clientes;

import com.customersu.dashapi.cases.pessoas.PessoaEntity;
import com.customersu.dashapi.cases.pessoas.PessoaService;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final PessoaService pessoaService;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public ClienteDtoResponse toDtoResponse(ClienteEntity entity) {
        return ClienteDtoResponse.builder()
                .id(entity.getId())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .pessoa(pessoaService.toDtoResponsePoliformPfPj(entity.getPessoa()))

                .build();
    }

// CRUD:
//  C - CREATE
    public ClienteDtoResponse criar(ClienteDtoRequest dto) {

        if (clienteRepository.existsByPessoaId(dto.getPessoa().getId())) {
            throw new RuntimeException("Cliente já cadastrado com este usuário.");
        }

        PessoaEntity registroPessoa = pessoaService.criarOuAtualizarRegistroPessoa(dto.getPessoa());

        ClienteEntity entity = ClienteEntity.builder()
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .pessoa(registroPessoa)

                .build();

        ClienteEntity salvo = clienteRepository.save(entity);

        return toDtoResponse(salvo);
    }

//  R - READ (por ID e todos)
    public ClienteDtoResponse buscarPorId(Long id) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public ClienteEntity buscarEntityPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
    }

    public ClienteDtoResponse buscarPorPessoaId(Long pessoaId) {
        ClienteEntity entity = clienteRepository.findByPessoaId(pessoaId)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public List<ClienteDtoResponse> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<ClienteDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return clienteRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public ClienteDtoResponse atualizar(Long pessoaId, ClienteDtoRequest dto) {
        ClienteEntity entity = clienteRepository.findByPessoaId(pessoaId)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        entity.setAtivo(dto.getAtivo());

            //Garantir atualizar dados da Pessoa (física ou jurídica)
            pessoaService.atualizar(pessoaId, dto.getPessoa());
            entity.setPessoa(pessoaService.buscarEntityPorId(pessoaId));

        ClienteEntity atualizada = clienteRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletarPorPessoaId(Long pessoaId) {
        if (!clienteRepository.existsByPessoaId(pessoaId)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        clienteRepository.deleteByPessoaId(pessoaId);
    }


}
