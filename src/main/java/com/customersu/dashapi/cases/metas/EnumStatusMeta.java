package com.customersu.dashapi.cases.metas;

import com.customersu.dashapi.cases.contas.EnumTipoConta;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumStatusMeta {
    ESTIPULADA, CONFIRMADA, ANDAMENTO, ATINGIDA, CANCELADA, VENCIDA;

    @JsonCreator
    public static EnumStatusMeta fromString(String value) {
        for (EnumStatusMeta tipo : EnumStatusMeta.values()) {
            if (tipo.name().equalsIgnoreCase(value)) return tipo;
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
