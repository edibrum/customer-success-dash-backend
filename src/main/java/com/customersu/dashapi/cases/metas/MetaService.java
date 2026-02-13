package com.customersu.dashapi.cases.metas;

import com.customersu.dashapi.cases.gerentes.GerenteService;
import com.customersu.dashapi.cases.produtos.ProdutoService;
import com.customersu.dashapi.cases.tarefas.EnumStatusTarefa;
import com.customersu.dashapi.cases.tarefas.EnumTipoTarefa;
import com.customersu.dashapi.cases.tarefas.TarefaDtoResponse;
import com.customersu.dashapi.cases.tarefas.TarefaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;

    private final GerenteService gerenteService;

    private final ProdutoService produtoService;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public MetaDtoResponse toDtoResponse(MetaEntity entity) {
        return MetaDtoResponse.builder()
                .id(entity.getId())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .inicio(entity.getInicio())
                .fim(entity.getFim())
                .descricao(entity.getDescricao())
                .status(entity.getStatus())
                .observacao(entity.getObservacao())
                .gerente(gerenteService.toDtoResponse(entity.getGerente()))
                .produto(entity.getProduto() != null ? produtoService.toDtoResponse(entity.getProduto()): null)
                .build();
    }

// CRUD:
//  C - CREATE
    public MetaDtoResponse criar(MetaDtoRequest dto) {

        if (dto.getGerenteId() == null) {
            throw new RuntimeException("Necessário associar um Gerente.");
        }

        if ((dto.getProdutoId() != null && dto.getStatus() != null) && metaRepository.existsByGerente_IdAndProduto_IdAndStatus(dto.getGerenteId(), dto.getProdutoId(), dto.getStatus())) {
            throw new RuntimeException("Meta já cadastrada com mesmos dados (Gerente, Produto e Status).");
        }

        MetaEntity entity = MetaEntity.builder()
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .inicio(dto.getInicio())
                .fim(dto.getFim())
                .descricao(dto.getDescricao())
                .status(dto.getStatus())
                .observacao(dto.getObservacao())
                .gerente(gerenteService.buscarEntityPorId(dto.getGerenteId()))
                .produto(dto.getProdutoId() != null ? produtoService.buscarEntityPorId(dto.getProdutoId()): null)
                .build();

        MetaEntity salva = metaRepository.save(entity);

        return toDtoResponse(salva);
    }

//  R - READ (por ID e todos)
    public MetaDtoResponse buscarPorId(Long id) {
        MetaEntity entity = metaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public MetaEntity buscarEntityPorId(Long id) {
        return metaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
    }

    public List<MetaDtoResponse> listarTodos() {
        return metaRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<MetaDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return metaRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

    public Page<MetaDtoResponse> listarComFiltros(Long gerenteId, Long produtoId, String statusMeta, LocalDate inicio, LocalDate fim, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Inicia com uma spec válida (sempre verdadeira)
        Specification<MetaEntity> spec = (root, query, cb) -> cb.conjunction();

        if (statusMeta != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), EnumStatusMeta.fromString(statusMeta)));
        }

        if (gerenteId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("gerente").get("id"), gerenteId));
        }

        if (produtoId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("produto").get("id"), produtoId));
        }

        if (inicio != null && fim != null) {
            // Ajustado para 'dataCriacao' e conversão de data para hora
            spec = spec.and((root, query, cb) -> cb.between(root.get("dataCriacao"), inicio.atStartOfDay(), fim.atTime(23, 59, 59)));
        }

        return metaRepository.findAll(spec, pageable).map(this::toDtoResponse);
    }

//  U - UPDATE
    public MetaDtoResponse atualizar(Long id, MetaDtoRequest dto) {
        MetaEntity entity = metaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        entity.setAtivo(dto.getAtivo());
        entity.setDescricao(dto.getDescricao());
        entity.setStatus(dto.getStatus());
        entity.setInicio(dto.getInicio());
        entity.setFim(dto.getFim());
        entity.setObservacao(dto.getObservacao());
        entity.setGerente(gerenteService.buscarEntityPorId(dto.getGerenteId()));
        entity.setProduto(dto.getProdutoId() != null ? produtoService.buscarEntityPorId(dto.getProdutoId()): null);

        MetaEntity atualizada = metaRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!metaRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        metaRepository.deleteById(id);
    }

}
