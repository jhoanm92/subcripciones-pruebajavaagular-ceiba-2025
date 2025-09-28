package com.api.btg.service;

import com.api.btg.dto.ClienteDto;

import java.util.List;

public interface ClienteService {

    List<ClienteDto> obtenerCLientes();
    ClienteDto obtenerCLientePorId(Integer id);
    ClienteDto actualizarCliente(ClienteDto cliente);
}
