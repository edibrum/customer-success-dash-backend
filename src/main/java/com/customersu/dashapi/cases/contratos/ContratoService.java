package com.customersu.dashapi.cases.contratos;

import com.customersu.dashapi.cases.contas.ContaService;
import com.customersu.dashapi.cases.produtos.ProdutoService;
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
public class ContratoService {

    private final ContratoRepository contratoRepository;

    private final ContaService contaService;

    private final ProdutoService produtoService;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public ContratoDtoResponse toDtoResponse(ContratoEntity entity) {
        return ContratoDtoResponse.builder()
                .id(entity.getId())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .vigenciaInicio(entity.getVigenciaInicio())
                .vigenciaFim(entity.getVigenciaFim())
                .observacao(entity.getObservacao())
                .conta(contaService.toDtoResponse(entity.getConta()))
                .produto(produtoService.toDtoResponse(entity.getProduto()))
                .build();
    }

// CRUD:
//  C - CREATE
    public ContratoDtoResponse criar(ContratoDtoRequest dto) {

        if (contratoRepository.existsByProduto_IdAndConta_Id(dto.getProdutoId(), dto.getContaId())) {
            throw new RuntimeException("Contrato já cadastrada com mesmos dados (Produto e Conta).");
        }

        ContratoEntity entity = ContratoEntity.builder()
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .vigenciaInicio(dto.getVigenciaInicio())
                .vigenciaFim(dto.getVigenciaFim())
                .observacao(dto.getObservacao())
                .conta(contaService.buscarEntityPorId(dto.getContaId()))
                .produto(produtoService.buscarEntityPorId(dto.getProdutoId()))
                .build();

        ContratoEntity salva = contratoRepository.save(entity);

        return toDtoResponse(salva);
    }

//  R - READ (por ID e todos)
    public ContratoDtoResponse buscarPorId(Long id) {
        ContratoEntity entity = contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public ContratoEntity buscarEntityPorId(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
    }

    public List<ContratoDtoResponse> listarTodos() {
        return contratoRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<ContratoDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return contratoRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public ContratoDtoResponse atualizar(Long id, ContratoDtoRequest dto) {
        ContratoEntity entity = contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        entity.setAtivo(dto.getAtivo());
        entity.setVigenciaInicio(dto.getVigenciaInicio());
        entity.setVigenciaFim(dto.getVigenciaFim());
        entity.setObservacao(dto.getObservacao());
        entity.setConta(contaService.buscarEntityPorId(dto.getContaId()));
        entity.setProduto(produtoService.buscarEntityPorId(dto.getProdutoId()));

        ContratoEntity atualizada = contratoRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        contratoRepository.deleteById(id);
    }

}
