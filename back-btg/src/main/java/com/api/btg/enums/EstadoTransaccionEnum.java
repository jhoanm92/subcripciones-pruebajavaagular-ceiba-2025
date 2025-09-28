package com.api.btg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoTransaccionEnum {

    APERTURA(1),
    CANCELAR(2);

    private final int idEstado;
}

