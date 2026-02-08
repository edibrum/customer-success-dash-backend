package com.customersu.dashapi.cases.contratos;

import com.customersu.dashapi.cases.contas.ContaDtoResponse;
import com.customersu.dashapi.cases.produtos.ProdutoDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContratoDtoResponse {
    private Long id;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDate vigenciaInicio;
    private LocalDate vigenciaFim;
    private String observacao;
    private ContaDtoResponse conta;
    private ProdutoDtoResponse produto;
}
