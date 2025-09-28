package com.api.btg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FondoDto {

    private Integer id;
    private String nombre;
    private BigDecimal montoVinculacion;
    private String categoria;

}
