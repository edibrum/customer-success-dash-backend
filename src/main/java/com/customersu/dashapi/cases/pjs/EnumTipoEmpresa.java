package com.customersu.dashapi.cases.pjs;

import com.customersu.dashapi.cases.metas.EnumStatusMeta;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumTipoEmpresa {
    MEI, LTDA, SA;

    @JsonCreator
    public static EnumTipoEmpresa fromString(String value) {
        for (EnumTipoEmpresa tipo : EnumTipoEmpresa.values()) {
            if (tipo.name().equalsIgnoreCase(value)) return tipo;
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
