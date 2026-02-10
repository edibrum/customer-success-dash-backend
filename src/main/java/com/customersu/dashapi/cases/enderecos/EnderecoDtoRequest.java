package com.customersu.dashapi.cases.enderecos;

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
public class EnderecoDtoRequest {
    private Boolean ativo;
    private Long id;
    private String complemento;
    private String observacao;

    @lombok.Builder.Default // builder respeitando o valor inicial/default
    @NotBlank(message = "Campo pais é obrigatório.")
    private String pais = "Brasil";

    @NotBlank(message = "Campo uf é obrigatório.")
    private String uf;

    @NotBlank(message = "Campo cidade é obrigatório.")
    private String cidade;

    @NotBlank(message = "Campo bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "Campo cep é obrigatório.")
    private String cep;

    @NotBlank(message = "Campo logradouro é obrigatório.")
    private String logradouro;

    @NotBlank(message = "Campo numero é obrigatório.")
    private String numero;

}
