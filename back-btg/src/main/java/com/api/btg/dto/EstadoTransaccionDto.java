package com.api.btg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadoTransaccionDto {

    private Integer id;
    private String estado;
    private String descripcion;
}