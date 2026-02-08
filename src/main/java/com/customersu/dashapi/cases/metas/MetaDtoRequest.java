package com.customersu.dashapi.cases.metas;

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
public class MetaDtoRequest {
    private Long id;
    private Boolean ativo;
    private String descricao;
    private LocalDate inicio;
    private LocalDate fim;
    private EnumStatusMeta status;
    private Long gerenteId;
    private Long produtoId;
    private String observacao;
}
