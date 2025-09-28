package com.api.btg.service.impl;

import com.api.btg.dto.TransaccionDto;
import com.api.btg.enums.ErrorsEnum;
import com.api.btg.exception.ModeloNotFoundException;
import com.api.btg.mapper.TransaccionDtoMapper;
import com.api.btg.repository.TransaccionRepository;
import com.api.btg.service.TransaccionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final TransaccionDtoMapper transaccionDtoMapper;

    @Override
    public List<TransaccionDto> obtenerTransaccionesPorCliente(Integer clienteId) {

        log.info("Service obtenerTransacciones");

        return transaccionRepository.findByClienteId(clienteId).stream()
                .map(transaccionDtoMapper::toDto)
                .toList();
    }


    @Override
    public TransaccionDto crearTransaccion(TransaccionDto transaccionDto) {
        return Optional.of(transaccionDto)
                .map(transaccionDtoMapper::toEntity)
                .map(transaccionRepository::save)
                .map(transaccionDtoMapper::toDto)
                .orElseThrow(() -> {

                    log.error("Ocurrio un error al crear la transaccion {}", transaccionDto);

                    return new ModeloNotFoundException(ErrorsEnum.ERROR_SUSCRIPCION.getMensaje());
                });
    }
}

