package com.customersu.dashapi.cases.pfs;

import com.customersu.dashapi.cases.pessoas.PessoaDtoRequest;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
@JsonTypeName("PF")
public class PfDtoRequest extends PessoaDtoRequest {
    private String rg;
    private String sexo;
    private String nomeSocial; //* obs.: inclusão
    private String artigoPreferencia; //* obs.: inclusão

    @NotBlank(message = "Campo cpf é obrigatório.")
    private String cpf;

    @NotNull(message = "Campo nascimento é obrigatório.")
    @PastOrPresent(message = "A data de nascimento não pode ser uma data futura.")
    private LocalDate nascimento;

    //OBS.: consideremos o campo "nome" no PessoaDtoRequest como a "nome completo da pessoa física" neste caso aqui
}
