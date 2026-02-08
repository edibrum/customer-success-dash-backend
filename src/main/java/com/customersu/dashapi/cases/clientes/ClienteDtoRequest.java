package com.customersu.dashapi.cases.clientes;

import com.customersu.dashapi.cases.pessoas.PessoaDtoRequest;
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
public class ClienteDtoRequest {
    private Long id;
    private Boolean ativo;
    private PessoaDtoRequest pessoa; //OBS.: polimórfico: PfDtoResponse ou PjDtoResponse
}
