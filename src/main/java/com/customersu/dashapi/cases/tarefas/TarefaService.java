package com.customersu.dashapi.cases.tarefas;

import com.customersu.dashapi.cases.gerentes.GerenteService;
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
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    private final GerenteService gerenteService;

    private final ProdutoService produtoService;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public TarefaDtoResponse toDtoResponse(TarefaEntity entity) {
        return TarefaDtoResponse.builder()
                .id(entity.getId())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .descricao(entity.getDescricao())
                .status(entity.getStatus())
                .tipo(entity.getTipo())
                .observacao(entity.getObservacao())
                .gerente(gerenteService.toDtoResponse(entity.getGerente()))
                .metaId(entity.getMetaId())
                .clienteId(entity.getClienteId())
                .contratoId(entity.getContratoId())
                .produtoId(entity.getProdutoId())

                .build();
    }

// CRUD:
//  C - CREATE
    public TarefaDtoResponse criar(TarefaDtoRequest dto) {

        if (tarefaRepository.existsById(dto.getId())) {
            throw new RuntimeException("Tarefa já cadastrada.");
        }

        TarefaEntity entity = TarefaEntity.builder()
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .descricao(dto.getDescricao())
                .status(dto.getStatus())
                .tipo(dto.getTipo())
                .observacao(dto.getObservacao())
                .gerente(gerenteService.buscarEntityPorId(dto.getGerenteId()))
                .metaId(dto.getMetaId())
                .clienteId(dto.getClienteId())
                .contratoId(dto.getContratoId())
                .produtoId(dto.getProdutoId())

                .build();

        TarefaEntity salva = tarefaRepository.save(entity);

        return toDtoResponse(salva);
    }

//  R - READ (por ID e todos)
    public TarefaDtoResponse buscarPorId(Long id) {
        TarefaEntity entity = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public TarefaEntity buscarEntityPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
    }

    public List<TarefaDtoResponse> listarTodos() {
        return tarefaRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<TarefaDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return tarefaRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public TarefaDtoResponse atualizar(Long id, TarefaDtoRequest dto) {
        TarefaEntity entity = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        entity.setAtivo(dto.getAtivo());
        entity.setDescricao(dto.getDescricao());
        entity.setStatus(dto.getStatus());
        entity.setTipo(dto.getTipo());
        entity.setObservacao(dto.getObservacao());
        entity.setGerente(gerenteService.buscarEntityPorId(dto.getGerenteId()));
        entity.setMetaId(dto.getMetaId());
        entity.setClienteId(dto.getClienteId());
        entity.setContratoId(dto.getContratoId());
        entity.setProdutoId(dto.getProdutoId());

        TarefaEntity atualizada = tarefaRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        tarefaRepository.deleteById(id);
    }

}
