package com.customersu.dashapi.cases.contas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumTipoConta {
    CORRENTE, POUPANCA, SALARIO;

    @JsonCreator
    public static EnumTipoConta fromString(String value) {
        for (EnumTipoConta tipo : EnumTipoConta.values()) {
            if (tipo.name().equalsIgnoreCase(value)) return tipo;
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
