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
public class ClienteDto {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String numeroIdentificacion;
    private BigDecimal creditos;
}
