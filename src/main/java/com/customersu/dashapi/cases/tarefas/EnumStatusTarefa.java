package com.customersu.dashapi.cases.tarefas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumStatusTarefa {
    CRIADA, EM_ANDAMENTO, REALIZADA, POSTERGADA, CANCELADA;

    @JsonCreator
    public static EnumStatusTarefa fromString(String value) {
        for (EnumStatusTarefa tipo : EnumStatusTarefa.values()) {
            if (tipo.name().equalsIgnoreCase(value)) return tipo;
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
