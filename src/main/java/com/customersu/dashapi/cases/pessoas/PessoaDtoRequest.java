package com.customersu.dashapi.cases.pessoas;

import com.customersu.dashapi.cases.enderecos.EnderecoDtoRequest;
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
public class PessoaDtoRequest {
    private Long id;
    private Boolean ativo;
    private String nome;
    private String email;
    private String telefone;
    private EnderecoDtoRequest endereco;
}
