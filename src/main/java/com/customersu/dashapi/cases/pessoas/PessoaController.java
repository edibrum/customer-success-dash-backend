package com.customersu.dashapi.cases.pessoas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaDtoResponse> criar(@RequestBody PessoaDtoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDtoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PessoaDtoResponse>> listar() {
        return ResponseEntity.ok(pessoaService.listarTodos());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<PessoaDtoResponse>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                pessoaService.listarPaginado(page, size, sortBy, direction)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDtoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PessoaDtoRequest dto
    ) {
        return ResponseEntity.ok(pessoaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
