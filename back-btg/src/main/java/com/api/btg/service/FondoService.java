package com.api.btg.service;

import com.api.btg.dto.FondoDto;

import java.util.List;

public interface FondoService {

    List<FondoDto> obtenerFondos();
    FondoDto obtenerFondoPorId(Integer id);
}
