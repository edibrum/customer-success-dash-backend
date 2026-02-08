package com.customersu.dashapi.cases.contas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContaDtoRequest {
    private Long id;
    private Boolean ativo;
    private EnumTipoConta tipo;
    private String banco;
    private String agencia;
    private String numero;
    private String digito;
    private BigDecimal saldo;
    private Long titularId;
    private Long gerenteId;
}
