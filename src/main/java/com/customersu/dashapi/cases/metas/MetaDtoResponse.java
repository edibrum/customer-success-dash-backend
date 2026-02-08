package com.customersu.dashapi.cases.metas;

import com.customersu.dashapi.cases.gerentes.GerenteDtoResponse;
import com.customersu.dashapi.cases.produtos.ProdutoDtoResponse;
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
public class MetaDtoResponse {
    private Long id;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String descricao;
    private LocalDate inicio;
    private LocalDate fim;
    private EnumStatusMeta status;
    private GerenteDtoResponse gerente;
    private ProdutoDtoResponse produto;
    private String observacao;
}
