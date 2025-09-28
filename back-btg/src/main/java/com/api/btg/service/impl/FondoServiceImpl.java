package com.api.btg.service.impl;

import com.api.btg.dto.FondoDto;
import com.api.btg.enums.ErrorsEnum;
import com.api.btg.exception.ModeloNotFoundException;
import com.api.btg.mapper.FondoDtoMapper;
import com.api.btg.repository.FondoRepository;
import com.api.btg.service.FondoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.api.btg.util.Util.peek;

@Slf4j
@Service
@AllArgsConstructor
public class FondoServiceImpl implements FondoService {

    private final FondoRepository fondoRepository;
    private final FondoDtoMapper fondoDtoMapper;

    @Override
    public List<FondoDto> obtenerFondos() {
        log.info("Service obtenerFondos");

        return fondoRepository.findAll().stream()
                .map(fondoDtoMapper::toDto)
                .toList();
    }

    @Override
    public FondoDto obtenerFondoPorId(Integer id) {
        log.info("Service obtenerFondoPorId, ID del fondo {}", id);

        return fondoRepository.findById(id)
                .map(peek(cliente -> log.info("fondo encontrado: {}", cliente)))
                .map(fondoDtoMapper::toDto)
                .orElseThrow(() -> {

                    log.error("fondo con id: {} no encontrado", id);

                    return new ModeloNotFoundException(
                            String.format(
                                    ErrorsEnum.ERROR_CLENTE_NO_ENCONTRADO.getMensaje(), id));
                });
    }
}
