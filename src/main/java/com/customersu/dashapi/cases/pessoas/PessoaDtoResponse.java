package com.customersu.dashapi.cases.pessoas;

import com.customersu.dashapi.cases.enderecos.EnderecoDtoResponse;
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
public class PessoaDtoResponse {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private EnderecoDtoResponse endereco;
}
