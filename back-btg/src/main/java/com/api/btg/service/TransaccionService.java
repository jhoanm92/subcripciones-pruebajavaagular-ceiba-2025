package com.api.btg.service;

import com.api.btg.dto.TransaccionDto;

import java.util.List;

public interface TransaccionService {

    List<TransaccionDto> obtenerTransaccionesPorCliente(Integer clienteId);
    TransaccionDto crearTransaccion(TransaccionDto transaccionDto);
}
