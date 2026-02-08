package com.customersu.dashapi.cases.pjs;

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
public class PjDtoRequest extends PessoaDtoRequest {
    private String cnpj;
    private String nomeFantasia;
    private String inscEstadual;
    private String inscMunicipal;
    private LocalDate abertura;
    private String tipo;
    //OBS.: consideremos o campo "nome" no PessoaDtoRequest como a "razão social da empresa" neste caso aqui
}
