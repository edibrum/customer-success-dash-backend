package com.customersu.dashapi.cases.metas;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/metas")
public class MetaController {

    private final MetaService metaService;

    @PostMapping
    public ResponseEntity<MetaDtoResponse> criar(@RequestBody MetaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metaService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(metaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<MetaDtoResponse>> listar() {
        return ResponseEntity.ok(metaService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<MetaDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                metaService.listarPaginado(page, size, sortBy, direction)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody MetaDtoRequest dto
    ) {
        return ResponseEntity.ok(metaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
