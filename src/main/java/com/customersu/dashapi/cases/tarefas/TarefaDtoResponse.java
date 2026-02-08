package com.customersu.dashapi.cases.tarefas;

import com.customersu.dashapi.cases.gerentes.GerenteDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TarefaDtoResponse {
    private Long id;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private EnumStatusTarefa status;
    private EnumTipoTarefa tipo;
    private String descricao;
    private String observacao;
    private GerenteDtoResponse gerente;
    private Long metaId;
    private Long clienteId;
    private Long contratoId;
    private Long produtoId;
}
