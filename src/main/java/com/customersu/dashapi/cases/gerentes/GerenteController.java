package com.customersu.dashapi.cases.gerentes;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gerentes")
public class GerenteController {

    private final GerenteService gerenteService;

    @PostMapping
    public ResponseEntity<GerenteDtoResponse> criar(@RequestBody GerenteDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gerenteService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GerenteDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(gerenteService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<GerenteDtoResponse>> listar() {
        return ResponseEntity.ok(gerenteService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<GerenteDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                gerenteService.listarPaginado(page, size, sortBy, direction)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GerenteDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody GerenteDtoRequest dto
    ) {
        return ResponseEntity.ok(gerenteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        gerenteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
