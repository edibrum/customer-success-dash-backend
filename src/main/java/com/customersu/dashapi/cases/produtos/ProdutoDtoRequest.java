package com.customersu.dashapi.cases.produtos;

import jakarta.validation.constraints.NotBlank;
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
    private Boolean ativo;

    @NotBlank(message = "Campo código é obrigatório.")
    private String codigo;

    @NotBlank(message = "Campo descrição é obrigatório.")
    private String descricao;

    private String observacao;
}
