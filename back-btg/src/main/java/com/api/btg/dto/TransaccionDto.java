package com.api.btg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionDto {

    private Integer id;
    private ClienteDto cliente;
    private FondoDto fondo;
    private EstadoTransaccionDto estadoTransaccion;
    private LocalDateTime fecha;
}
