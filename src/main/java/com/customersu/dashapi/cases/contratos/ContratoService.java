package com.customersu.dashapi.cases.contratos;

import com.customersu.dashapi.cases.contas.ContaDtoResponse;
import com.customersu.dashapi.cases.contas.ContaEntity;
import com.customersu.dashapi.cases.contas.ContaService;
import com.customersu.dashapi.cases.contas.EnumTipoConta;
import com.customersu.dashapi.cases.pfs.PfEntity;
import com.customersu.dashapi.cases.pjs.PjEntity;
import com.customersu.dashapi.cases.produtos.ProdutoService;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


    public Page<ContratoDtoResponse> listarComFiltros(Long gerenteId, Long produtoId, String tipoConta, String tipoPessoa, LocalDate vigenciaInicio, LocalDate vigenciaFim, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Inicia com uma spec válida (sempre verdadeira)
        Specification<ContratoEntity> spec = (root, query, cb) -> cb.conjunction();

        if (produtoId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("produto").get("id"), produtoId));
        }

        if (tipoConta != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("conta").get("tipo"), EnumTipoConta.fromString(tipoConta)));
        }

        if (gerenteId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("conta").get("gerente").get("id"), gerenteId));
        }

        if (tipoPessoa != null) {
            spec = spec.and((root, query, cb) -> {
                Join<?, ?> pessoaJoin = root.join("conta").join("titular").join("pessoa");

                if (tipoPessoa.equalsIgnoreCase("PF")) {
                    return cb.equal(pessoaJoin.type(), PfEntity.class);
                } else if (tipoPessoa.equalsIgnoreCase("PJ")) {
                    return cb.equal(pessoaJoin.type(), PjEntity.class);
                } else {
                    return cb.conjunction();
                }
            });
        }

        if (vigenciaInicio != null && vigenciaFim != null) {
            LocalDateTime start = vigenciaInicio.atStartOfDay();
            LocalDateTime end = vigenciaFim.atTime(LocalTime.MAX);

            spec = spec.and((root, query, cb) -> {
                //
                return cb.and(
                        cb.lessThanOrEqualTo(root.get("vigenciaInicio"), end),
                        cb.greaterThanOrEqualTo(root.get("vigenciaFim"), start)
                );
            });
        }

        return contratoRepository.findAll(spec, pageable).map(this::toDtoResponse);
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
