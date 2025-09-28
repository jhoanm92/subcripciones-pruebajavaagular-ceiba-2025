package com.api.btg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuscripcionDto {

    private ClienteDto cliente;
    private FondoDto fondo;
}
