package com.api.btg.dto.request;

import com.api.btg.enums.EstadoTransaccionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuscripcionFondoRequest {

    private Integer clienteId;
    private Integer fondoId;
    private EstadoTransaccionEnum estadoTransaccion;
}
