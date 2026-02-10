package com.customersu.dashapi.cases.pessoas;

import com.customersu.dashapi.cases.enderecos.EnderecoDtoResponse;
import com.customersu.dashapi.cases.pfs.PfDtoResponse;
import com.customersu.dashapi.cases.pjs.PjDtoResponse;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoPessoa"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PfDtoResponse.class, name = "PF"),
        @JsonSubTypes.Type(value = PjDtoResponse.class, name = "PJ")
})
public class PessoaDtoResponse {
    private String tipoPessoa;
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private EnderecoDtoResponse endereco;
}
