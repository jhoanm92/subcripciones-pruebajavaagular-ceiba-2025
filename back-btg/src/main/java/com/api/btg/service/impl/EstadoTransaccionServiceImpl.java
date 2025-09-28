package com.api.btg.service.impl;

import com.api.btg.dto.EstadoTransaccionDto;
import com.api.btg.enums.ErrorsEnum;
import com.api.btg.exception.ModeloNotFoundException;
import com.api.btg.mapper.EstadoTransaccionDtoMapper;
import com.api.btg.repository.EstadoTransaccionRepository;
import com.api.btg.service.EstadoTransaccionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.api.btg.util.Util.peek;

@Slf4j
@Service
@AllArgsConstructor
public class EstadoTransaccionServiceImpl implements EstadoTransaccionService {

    private final EstadoTransaccionRepository estadoTransaccionRepository;
    private final EstadoTransaccionDtoMapper estadoTransaccionDtoMapper;

    @Override
    public List<EstadoTransaccionDto> obtenerEstadoTransaccion() {

        log.info("Service obtenerEstadoTransaccion");

        return estadoTransaccionRepository.findAll().stream()
                .map(estadoTransaccionDtoMapper::toDto)
                .toList();
    }

    @Override
    public EstadoTransaccionDto obtenerEstadoTransaccionPorId(Integer id) {
        log.info("Service obtenerEstadoTransaccionPorId, ID del estado {}", id);

        return estadoTransaccionRepository.findById(id)
                .map(peek(estado -> log.info("estadoTransaccion encontrado: {}", estado)))
                .map(estadoTransaccionDtoMapper::toDto)
                .orElseThrow(() -> {

                    log.error("estado con id: {} no encontrado", id);

                    return new ModeloNotFoundException(
                            String.format(
                                    ErrorsEnum.ERROR_ESTADO_NO_ENCONTRADO.getMensaje(), id));
                });
    }
}
