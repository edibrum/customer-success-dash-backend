package com.customersu.dashapi.cases.pessoas;

import com.customersu.dashapi.cases.enderecos.EnderecoDtoRequest;
import com.customersu.dashapi.cases.pfs.PfDtoRequest;
import com.customersu.dashapi.cases.pjs.PjDtoRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoPessoa"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PfDtoRequest.class, name = "PF"),
        @JsonSubTypes.Type(value = PjDtoRequest.class, name = "PJ")
})
public class PessoaDtoRequest {
    private Long id;
    private Boolean ativo;
    private String nome;

    @NotBlank(message = "Campo tipoPessoa (PF ou PJ) é obrigatório.")
    private String tipoPessoa;

    @NotBlank(message = "Campo email é obrigatório.")
    private String email;

    @NotBlank(message = "Campo telefone é obrigatório.")
    private String telefone;

    @NotNull(message = "Campo endereco é obrigatório")
    @Valid
    private EnderecoDtoRequest endereco;
}
