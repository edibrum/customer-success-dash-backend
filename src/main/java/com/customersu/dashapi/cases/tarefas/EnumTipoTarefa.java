package com.customersu.dashapi.cases.tarefas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumTipoTarefa {
    VISITA_LOCAL, CONTATO_TELEFONICO, COBRANCA, OUTRO;

    @JsonCreator
    public static EnumTipoTarefa fromString(String value) {
        for (EnumTipoTarefa tipo : EnumTipoTarefa.values()) {
            if (tipo.name().equalsIgnoreCase(value)) return tipo;
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
