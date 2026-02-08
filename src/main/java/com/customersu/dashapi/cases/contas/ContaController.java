package com.customersu.dashapi.cases.contas;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaDtoResponse> criar(@RequestBody ContaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ContaDtoResponse>> listar() {
        return ResponseEntity.ok(contaService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<ContaDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                contaService.listarPaginado(page, size, sortBy, direction)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ContaDtoRequest dto
    ) {
        return ResponseEntity.ok(contaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
