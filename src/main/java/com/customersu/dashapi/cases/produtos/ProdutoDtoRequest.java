package com.customersu.dashapi.cases.produtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProdutoDtoRequest {
    private Long id;
    private Boolean ativo;
    private String codigo;
    private String descricao;
    private String observacao;
}
