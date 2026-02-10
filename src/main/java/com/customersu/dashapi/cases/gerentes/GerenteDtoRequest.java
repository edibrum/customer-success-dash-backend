package com.customersu.dashapi.cases.gerentes;

import com.customersu.dashapi.cases.pessoas.PessoaDtoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class GerenteDtoRequest {
    private Long id;
    private Boolean ativo;
    private LocalDate admissao;

    @NotNull(message = "Campo pessoa é obrigatório")
    @Valid
    private PessoaDtoRequest pessoa; //OBS.: polimórfico: PfDtoResponse ou PjDtoResponse
}
