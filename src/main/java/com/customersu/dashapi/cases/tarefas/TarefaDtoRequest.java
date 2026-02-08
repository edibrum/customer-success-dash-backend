package com.customersu.dashapi.cases.tarefas;

import com.customersu.dashapi.cases.gerentes.GerenteDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TarefaDtoRequest {
    private Long id;
    private Boolean ativo;
    private EnumStatusTarefa status;
    private EnumTipoTarefa tipo;
    private String descricao;
    private String observacao;
    private Long gerenteId;
    private Long metaId;
    private Long clienteId;
    private Long contratoId;
    private Long produtoId;
}
