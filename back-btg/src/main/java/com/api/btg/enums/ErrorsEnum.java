package com.api.btg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorsEnum {

    ERROR_CLENTE_NO_ENCONTRADO("Cliente no encontrado con id: %s"),
    ERROR_ESTADO_NO_ENCONTRADO("Estado no encontrado con id: %s"),
    ERROR_FONDO_NO_ENCONTRADO("Fondo no encontrado con id: %s"),
    ERROR_SUSCRIPCION_NO_ENCONTRADA("Suscripcion con clienteId: %s y fondoId: %s no encontrado"),
    ERROR_SUSCRIPCION_ENCONTRADA("Ya existe una suscripcion para cliente %s y fondo %s"),

    ERROR_SALDO_INSUFUCIENTE("No tiene saldo disponible para vincularse al fondo %s"),


    ERROR_SUSCRIPCION("Ocurrio un error en la suscripcion"),
    ERROR_SUSCRIPCION_ESTADO("Estado de transacción no soportado %s")


    ;

    private final String mensaje;
}
