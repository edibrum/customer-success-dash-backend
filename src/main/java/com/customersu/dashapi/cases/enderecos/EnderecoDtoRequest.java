package com.customersu.dashapi.cases.enderecos;

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
    private String pais;
    private String uf;
    private String cidade;
    private String bairro;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String observacao;
}
