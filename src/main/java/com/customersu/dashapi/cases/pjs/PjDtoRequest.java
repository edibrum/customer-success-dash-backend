package com.customersu.dashapi.cases.pjs;

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
@JsonTypeName("PJ")
public class PjDtoRequest extends PessoaDtoRequest {
    private String nomeFantasia;
    private String inscEstadual;
    private String inscMunicipal;

    @NotNull(message = "Campo abertura é obrigatório.")
    @PastOrPresent(message = "A data de abertura não pode ser uma data futura.")
    private LocalDate abertura;

    @NotBlank(message = "Campo cnpj é obrigatório.")
    private String cnpj;

    @NotBlank(message = "Campo tipoEmpresa é obrigatório.")
    private EnumTipoEmpresa tipoEmpresa;
    //OBS.: consideremos o campo "nome" no PessoaDtoRequest como a "razão social da empresa" neste caso aqui
}
