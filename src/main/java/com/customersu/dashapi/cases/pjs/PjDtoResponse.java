package com.customersu.dashapi.cases.pjs;

import com.customersu.dashapi.cases.pessoas.PessoaDtoResponse;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Column;
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
@JsonTypeName("PJ")
public class PjDtoResponse extends PessoaDtoResponse {
    private String cnpj;
    private String nomeFantasia;
    private String inscEstadual;
    private String inscMunicipal;
    private LocalDate abertura;
    private EnumTipoEmpresa tipoEmpresa;
    //OBS.: consideremos o campo "nome" no PessoaDtoResponse como a "razão social da empresa" neste caso aqui
}