package com.customersu.dashapi.cases.contratos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContratoDtoRequest {
    private Long id;
    private Boolean ativo;
    private LocalDate vigenciaInicio;
    private LocalDate vigenciaFim;
    private String observacao;
    private Long contaId;
    private Long produtoId;
}
