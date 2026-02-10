package com.customersu.dashapi.cases.tarefas;

import jakarta.validation.constraints.NotBlank;
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
    private String observacao;
    private Long metaId;
    private Long clienteId;
    private Long contratoId;
    private Long produtoId;

    @NotBlank(message = "Campo status é obrigatório. Valores aceitos: CRIADA, EM_ANDAMENTO, REALIZADA, POSTERGADA, CANCELADA.")
    private EnumStatusTarefa status;

    @NotBlank(message = "Campo tipo é obrigatório. Valores aceitos: VISITA_LOCAL, CONTATO_TELEFONICO, COBRANCA, OUTRO.")
    private EnumTipoTarefa tipo;

    @NotBlank(message = "Campo descricao é obrigatório.")
    private String descricao;

    @NotBlank(message = "Campo gerenteId é obrigatório.")
    private Long gerenteId;

}
