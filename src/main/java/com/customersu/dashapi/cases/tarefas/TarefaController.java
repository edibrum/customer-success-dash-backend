package com.customersu.dashapi.cases.tarefas;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDtoResponse> criar(@RequestBody TarefaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TarefaDtoResponse>> listar() {
        return ResponseEntity.ok(tarefaService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<TarefaDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                tarefaService.listarPaginado(page, size, sortBy, direction)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody TarefaDtoRequest dto
    ) {
        return ResponseEntity.ok(tarefaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
