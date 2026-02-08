package com.customersu.dashapi.cases.pjs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PjService {

    private final PjRepository pjRepository;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    private PjDtoResponse toDtoResponse(PjEntity entity) {
        return PjDtoResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .ativo(entity.getAtivo())
                //específico PJ:
                .cnpj(entity.getCnpj())
                .nomeFantasia(entity.getNomeFantasia())
                .inscEstadual(entity.getInscEstadual())
                .inscMunicipal(entity.getInscMunicipal())
                .abertura(entity.getAbertura())
                .tipo(entity.getTipo())

                .build();
    }


//  DtoRequest para Entity:
    private PjEntity toEntity(PjDtoRequest request) {
        return PjEntity.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .ativo(request.getAtivo())
                //específico PJ:
                .cnpj(request.getCnpj())
                .nomeFantasia(request.getNomeFantasia())
                .inscEstadual(request.getInscEstadual())
                .inscMunicipal(request.getInscMunicipal())
                .abertura(request.getAbertura())
                .tipo(request.getTipo())

                .build();
    }

    private void updateEntity(PjEntity entity, PjDtoRequest request) {
        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone());
        entity.setAtivo(request.getAtivo());
        // específicos PJ
        entity.setCnpj(request.getCnpj());
        entity.setNomeFantasia(request.getNomeFantasia());
        entity.setInscEstadual(request.getInscEstadual());
        entity.setInscMunicipal(request.getInscMunicipal());
        entity.setAbertura(request.getAbertura());
        entity.setTipo(request.getTipo());
    }

// CRUD:
//  C - CREATE
    public PjDtoResponse criar(PjDtoRequest request) {

        if (pjRepository.existsByCnpj(request.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado");
        }

        PjEntity entity = toEntity(request);
        PjEntity salvo = pjRepository.save(entity);

        return toDtoResponse(salvo);
    }

//  R - READ (por ID e todos)
    public PjDtoResponse buscarPorId(Long id) {
        PjEntity entity = pjRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public List<PjDtoResponse> listarTodos() {
        return pjRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<PjDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return pjRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public PjDtoResponse atualizar(Long id, PjDtoRequest request) {

        PjEntity entity = pjRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        updateEntity(entity, request);

        PjEntity atualizado = pjRepository.save(entity);
        return toDtoResponse(atualizado);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!pjRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        pjRepository.deleteById(id);
    }

}
