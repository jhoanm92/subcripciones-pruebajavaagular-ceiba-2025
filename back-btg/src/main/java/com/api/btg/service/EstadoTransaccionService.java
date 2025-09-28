package com.api.btg.service;

import com.api.btg.dto.EstadoTransaccionDto;

import java.util.List;

public interface EstadoTransaccionService {

    List<EstadoTransaccionDto> obtenerEstadoTransaccion();
    EstadoTransaccionDto obtenerEstadoTransaccionPorId(Integer id);

}
