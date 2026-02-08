package com.customersu.dashapi.cases.gerentes;

import com.customersu.dashapi.cases.pessoas.PessoaDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GerenteDtoResponse {
    private Long id;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDate admissao;
    private PessoaDtoResponse pessoa; //OBS.: polimórfico: PfDtoResponse ou PjDtoResponse
}
