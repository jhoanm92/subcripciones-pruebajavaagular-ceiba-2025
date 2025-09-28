package com.api.btg.service;

import com.api.btg.dto.SuscripcionDto;
import com.api.btg.dto.request.SuscripcionFondoRequest;
import com.api.btg.entity.SuscripcionId;

import java.util.List;

public interface SuscripcionService {

    List<SuscripcionDto> obtenerSuscripcionesPorIdCliente(Integer clienteId);
    SuscripcionDto obtenerSuscripcionPorIds(Integer clienteId, Integer fondoId);
    SuscripcionDto obtenerSuscripcionPorId(SuscripcionId suscripcionId);
    SuscripcionDto manejarSuscripcion(SuscripcionFondoRequest suscripcionFondoRequest);
    SuscripcionDto crearSuscripcion(SuscripcionFondoRequest suscripcionFondoRequest);
    SuscripcionDto cancelarSuscripcion(SuscripcionFondoRequest suscripcionFondoRequest);
    void eliminarSuscripcion(SuscripcionId suscripcionId);
}
