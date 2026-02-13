package com.customersu.dashapi.cases.contratos;

import com.customersu.dashapi.cases.contas.ContaDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResumoPorProdutosDtoResponse {
    private String produtoCodigo;
    private String produtoDescricao;
    private Page<ContratoDtoResponse> contratosVigentes;
    private Page<ContaDtoResponse> contasGapDoProduto;
}
