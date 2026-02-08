package com.customersu.dashapi.cases.pjs;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pjs")
public class PjController {

    private final PjService pjService;

    public PjController(PjService pjService) {
        this.pjService = pjService;
    }

    @PostMapping
    public ResponseEntity<PjDtoResponse> criar(@RequestBody PjDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pjService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PjDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pjService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PjDtoResponse>> listar() {
        return ResponseEntity.ok(pjService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<PjDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                pjService.listarPaginado(page, size, sortBy, direction)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PjDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PjDtoRequest dto
    ) {
        return ResponseEntity.ok(pjService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pjService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
