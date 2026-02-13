package com.customersu.dashapi.cases.contratos;

import com.customersu.dashapi.cases.contas.ContaDtoResponse;
import com.customersu.dashapi.cases.contas.EnumTipoConta;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    @PostMapping
    public ResponseEntity<ContratoDtoResponse> criar(@RequestBody ContratoDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoService.criar(dto));
    }

    @GetMapping("/buscar-por-id-contrato/{id}")
    public ResponseEntity<ContratoDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.buscarPorId(id));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ContratoDtoResponse>> listar() {
        return ResponseEntity.ok(contratoService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<ContratoDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                contratoService.listarPaginado(page, size, sortBy, direction)
        );
    }


    @GetMapping("/filtrar")
    public ResponseEntity<Page<ContratoDtoResponse>> filtrar(
            @RequestParam(required = true) Long gerenteId,
            @RequestParam(required = false) String produtoCodigo,
            @RequestParam(required = false) String tipoConta,
            @RequestParam(required = false) String tipoPessoa,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vigenciaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vigenciaFim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                contratoService.listarComFiltros(gerenteId, produtoCodigo, tipoConta, tipoPessoa, vigenciaInicio, vigenciaFim, page, size, sortBy, direction)
        );
    }

    @GetMapping("/resumo-por-produtos-contratados/{gerenteId}")
    public ResponseEntity<List<ResumoPorProdutosDtoResponse>> resumoPorProdutosContratados(
            @PathVariable Long gerenteId
    ) {
        return ResponseEntity.ok(
                contratoService.resumoPorProdutosContratados(gerenteId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratoDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ContratoDtoRequest dto
    ) {
        return ResponseEntity.ok(contratoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contratoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
