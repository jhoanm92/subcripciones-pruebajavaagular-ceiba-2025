package com.api.btg.service.impl;

import com.api.btg.dto.ClienteDto;
import com.api.btg.enums.ErrorsEnum;
import com.api.btg.exception.ModeloNotFoundException;
import com.api.btg.mapper.ClienteDtoMapper;
import com.api.btg.repository.ClienteRepository;
import com.api.btg.service.ClienteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.api.btg.util.Util.peek;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteDtoMapper clienteDtoMapper;

    @Override
    public List<ClienteDto> obtenerCLientes() {

        log.info("Service obtenerCLientes");

        return clienteRepository.findAll().stream()
                .map(clienteDtoMapper::toDto)
                .toList();
    }

    @Override
    public ClienteDto obtenerCLientePorId(Integer id) {

        log.info("Service obtenerCLientePorId, ID del cliente {}", id);

        return clienteRepository.findById(id)
                .map(peek(cliente -> log.info("Cliente encontrado: {}", cliente)))
                .map(clienteDtoMapper::toDto)
                .orElseThrow(() -> {

                    log.error("Cliente con id: {} no encontrado", id);

                    return new ModeloNotFoundException(
                            String.format(
                                    ErrorsEnum.ERROR_CLENTE_NO_ENCONTRADO.getMensaje(), id));
                });
    }

    @Override
    public ClienteDto actualizarCliente(ClienteDto cliente) {

        clienteRepository.findById(cliente.getId());

        return Optional.of(cliente)
                .map(clienteDtoMapper::toEntity)
                .map(clienteRepository::save)
                .map(peek(c -> log.info("Cliente actualizado: {}", c)))
                .map(clienteDtoMapper::toDto)
                .orElse(null);
    }
}
