package com.customersu.dashapi.cases.pfs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pfs")
public class PfController {

    private final PfService pfService;

    @PostMapping
    public ResponseEntity<PfDtoResponse> criar(@RequestBody @Valid PfDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pfService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PfDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pfService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PfDtoResponse>> listar() {
        return ResponseEntity.ok(pfService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<PfDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                pfService.listarPaginado(page, size, sortBy, direction)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PfDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PfDtoRequest dto
    ) {
        return ResponseEntity.ok(pfService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pfService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
