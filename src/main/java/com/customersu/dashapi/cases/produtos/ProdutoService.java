package com.customersu.dashapi.cases.produtos;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public ProdutoDtoResponse toDtoResponse(ProdutoEntity entity) {
        return ProdutoDtoResponse.builder()
                .id(entity.getId())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .codigo(entity.getCodigo())
                .descricao(entity.getDescricao())
                .observacao(entity.getObservacao())
                .build();
    }

// CRUD:
//  C - CREATE
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProdutoDtoResponse criar(ProdutoDtoRequest dto) {

        if (produtoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Produto já cadastrado com o código(" + dto.getCodigo() + ") - usar rota UPDATE");
        }

        ProdutoEntity entity = ProdutoEntity.builder()
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .codigo(dto.getCodigo())
                .descricao(dto.getDescricao())
                .observacao(dto.getObservacao())
                .build();

        ProdutoEntity salva = produtoRepository.save(entity);

        return toDtoResponse(salva);
    }

//  R - READ (por ID e todos)
    public ProdutoDtoResponse buscarPorId(Long id) {
        ProdutoEntity entity = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public ProdutoEntity buscarEntityPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
    }

    public List<ProdutoDtoResponse> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<ProdutoDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return produtoRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public ProdutoDtoResponse atualizar(Long id, ProdutoDtoRequest dto) {
        ProdutoEntity entity = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        entity.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : entity.getAtivo());
        entity.setCodigo(dto.getCodigo());
        entity.setDescricao(dto.getDescricao());
        entity.setObservacao(dto.getObservacao());

        ProdutoEntity atualizada = produtoRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        produtoRepository.deleteById(id);
    }

}
