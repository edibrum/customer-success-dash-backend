package com.customersu.dashapi.cases.enderecos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EnderecoDtoResponse {
    private Long id;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
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
