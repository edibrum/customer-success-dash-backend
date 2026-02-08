package com.customersu.dashapi.cases.contas;

import com.customersu.dashapi.cases.clientes.ClienteDtoResponse;
import com.customersu.dashapi.cases.gerentes.GerenteDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContaDtoResponse {
    private Long id;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private EnumTipoConta tipo;
    private String banco;
    private String agencia;
    private String numero;
    private String digito;
    private BigDecimal saldo;
    private ClienteDtoResponse titular;
    private GerenteDtoResponse gerente;
}
