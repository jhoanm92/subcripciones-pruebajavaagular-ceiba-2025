package com.api.btg.service.impl;

import com.api.btg.dto.EstadoTransaccionDto;
import com.api.btg.dto.FondoDto;
import com.api.btg.dto.SuscripcionDto;
import com.api.btg.dto.TransaccionDto;
import com.api.btg.dto.request.SuscripcionFondoRequest;
import com.api.btg.entity.SuscripcionId;
import com.api.btg.enums.ErrorsEnum;
import com.api.btg.enums.EstadoTransaccionEnum;
import com.api.btg.exception.ModeloNotFoundException;
import com.api.btg.mapper.ClienteDtoMapper;
import com.api.btg.mapper.SuscripcionDtoMapper;
import com.api.btg.repository.SuscripcionRepository;
import com.api.btg.service.ClienteService;
import com.api.btg.service.FondoService;
import com.api.btg.service.SuscripcionService;
import com.api.btg.service.TransaccionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.api.btg.util.Util.peek;

@Slf4j
@Service
@AllArgsConstructor
public class SuscripcionServiceImpl implements SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final SuscripcionDtoMapper suscripcionDtoMapper;
    private final ClienteService clienteService;
    private final ClienteDtoMapper clienteDtoMapper;
    private final FondoService fondoService;
    private final TransaccionService transaccionService;

    @Override
    public List<SuscripcionDto> obtenerSuscripcionesPorIdCliente(Integer clienteId) {

        log.info("Service obtenerTransacciones");

        return suscripcionRepository.findByClienteId(clienteId).stream()
                .map(suscripcionDtoMapper::toDto)
                .toList();
    }

    @Override
    public SuscripcionDto obtenerSuscripcionPorIds(Integer clienteId, Integer fondoId) {

        log.info("Service obtenerSuscripcionPorIds, clienteId {} y fondoId {}", clienteId, fondoId);

        return suscripcionRepository.findByIdClienteIdAndIdFondoId(clienteId, fondoId)
                .map(peek(suscripcion -> log.info("Suscripcion con clienteId {} y fondoId {} encontrada: {}",
                        clienteId,
                        fondoId,
                        suscripcion)))
                .map(suscripcionDtoMapper::toDto)
                .orElseThrow(() -> {

                    log.error("Suscripcion clienteId {} y fondoId {} no encontrada", clienteId, fondoId);

                    return new ModeloNotFoundException(
                            String.format(
                                    ErrorsEnum.ERROR_SUSCRIPCION_NO_ENCONTRADA.getMensaje(), clienteId, fondoId));
                });
    }

    @Override
    public SuscripcionDto obtenerSuscripcionPorId(SuscripcionId suscripcionId) {

        log.info("Service obtenerSuscripcionPorId, clienteId {} y fondoId {}", suscripcionId.getClienteId(),
                suscripcionId.getFondoId());

        return suscripcionRepository.findById(suscripcionId)
                .map(peek(suscripcion -> log.info("Suscripcion con clienteId {} y fondoId {} encontrada: {}",
                        suscripcionId.getClienteId(),
                        suscripcionId.getFondoId(),
                        suscripcion)))
                .map(suscripcionDtoMapper::toDto)
                .orElseThrow(() -> {

                    log.error("Suscripcion clienteId {} y fondoId {} no encontrada", suscripcionId.getClienteId(),
                            suscripcionId.getFondoId());

                    return new ModeloNotFoundException(
                            String.format(
                                    ErrorsEnum.ERROR_SUSCRIPCION_NO_ENCONTRADA.getMensaje(),
                                    suscripcionId.getClienteId(),
                                    suscripcionId.getFondoId()));
                });
    }

    @Override
    public SuscripcionDto manejarSuscripcion(SuscripcionFondoRequest suscripcionFondoRequest) {
        log.info("Service manejarTransaccionSuscripcion, request: {}", suscripcionFondoRequest);

        // Usamos switch para decidir la lógica
        switch (suscripcionFondoRequest.getEstadoTransaccion()) {
            case APERTURA -> {
                log.info("Procesando apertura de transaccion");
                return crearSuscripcion(suscripcionFondoRequest);
            }
            case CANCELAR -> {
                log.info("Procesando cancelacion de transaccion");
                return cancelarSuscripcion(suscripcionFondoRequest);
            }
            default -> {
                log.error("Estado de transacción no reconocido: {}", suscripcionFondoRequest.getEstadoTransaccion());
                throw new ModeloNotFoundException(ErrorsEnum.ERROR_SUSCRIPCION_ESTADO.getMensaje());
            }
        }
    }

    @Override
    @Transactional
    public SuscripcionDto crearSuscripcion(SuscripcionFondoRequest suscripcionFondoRequest) {

        return Optional.of(suscripcionFondoRequest)
                .map(peek(this::validarSuscripcion))
                .map(this::consultarClienteYFondo)
                .map(peek(this::validarYActualizarCreditosCliente))
                .map(suscripcionDtoMapper::toEntity)
                .map(suscripcionRepository::save)
                .map(suscripcionDtoMapper::toDto)
                .map(peek(suscripcion -> this.crearTransaccion(suscripcion, suscripcionFondoRequest.getEstadoTransaccion())))
                .orElseThrow(() -> {
                    log.error("Ocurrio un error en la suscripcion {}", suscripcionFondoRequest);

                    return new ModeloNotFoundException(ErrorsEnum.ERROR_SUSCRIPCION.getMensaje());
                });
    }

    @Override
    @Transactional
    public SuscripcionDto cancelarSuscripcion(SuscripcionFondoRequest suscripcionFondoRequest) {

        return Optional.of(suscripcionFondoRequest)
                .map(sf -> this.obtenerSuscripcionPorIds(sf.getClienteId(), sf.getFondoId()))
                .map(peek(this::devolverCreditosCliente))
                .map(peek(this::eliminarSuscripcionPorSuscripcionId))
                .map(peek(suscripcion -> this.crearTransaccion(suscripcion, suscripcionFondoRequest.getEstadoTransaccion())))
                .orElseThrow(() -> {
                    log.error("Ocurrio un error en la suscripcion {}", suscripcionFondoRequest);

                    return new ModeloNotFoundException(ErrorsEnum.ERROR_SUSCRIPCION.getMensaje());
                });
    }

    @Override
    public void eliminarSuscripcion(SuscripcionId suscripcionId) {

        this.obtenerSuscripcionPorId(suscripcionId);

        suscripcionRepository.deleteById(suscripcionId);
    }

    private void validarSuscripcion(SuscripcionFondoRequest suscripcionFondoRequest) {

        try {
            obtenerSuscripcionPorIds(suscripcionFondoRequest.getClienteId(), suscripcionFondoRequest.getFondoId());
        } catch (Exception ex) {
            return;
        }

        log.info("Ya existe una suscripcion para cliente {} y fondo {}", suscripcionFondoRequest.getClienteId(),
                suscripcionFondoRequest.getFondoId());

        throw new ModeloNotFoundException(
                String.format(ErrorsEnum.ERROR_SUSCRIPCION_ENCONTRADA.getMensaje(),
                        suscripcionFondoRequest.getClienteId(), suscripcionFondoRequest.getFondoId()));
    }

    private SuscripcionDto consultarClienteYFondo(SuscripcionFondoRequest suscripcionFondoRequest) {
        return SuscripcionDto.builder()
                .cliente(clienteService.obtenerCLientePorId(suscripcionFondoRequest.getClienteId()))
                .fondo(fondoService.obtenerFondoPorId(suscripcionFondoRequest.getFondoId()))
                .build();
    }

    private void validarCreditos(FondoDto fondo, BigDecimal creditos) {

        if (creditos.compareTo((fondo.getMontoVinculacion())) < 0) {

            throw new ModeloNotFoundException(
                    String.format(ErrorsEnum.ERROR_SALDO_INSUFUCIENTE.getMensaje(), fondo.getNombre())
            );
        }
    }

    private BigDecimal restarCreditosCliente(BigDecimal ceditosFondo, BigDecimal creditosCliente) {

        return creditosCliente.subtract(ceditosFondo);
    }

    private BigDecimal sumarCreditosCliente(BigDecimal ceditosFondo, BigDecimal creditosCliente) {

        return creditosCliente.add(ceditosFondo);
    }

    private void validarYActualizarCreditosCliente(SuscripcionDto suscripcionDto) {

        this.validarCreditos(suscripcionDto.getFondo(), suscripcionDto.getCliente().getCreditos());

        suscripcionDto.getCliente().setCreditos(
                this.restarCreditosCliente(
                        suscripcionDto.getFondo().getMontoVinculacion(),
                        suscripcionDto.getCliente().getCreditos()
                )
        );
        clienteService.actualizarCliente(suscripcionDto.getCliente());
    }

    private void devolverCreditosCliente(SuscripcionDto suscripcionDto) {

        suscripcionDto.getCliente().setCreditos(
                this.sumarCreditosCliente(
                        suscripcionDto.getFondo().getMontoVinculacion(),
                        suscripcionDto.getCliente().getCreditos()
                )
        );
        clienteService.actualizarCliente(suscripcionDto.getCliente());
    }

    public void eliminarSuscripcionPorSuscripcionId(SuscripcionDto suscripcionDto) {

        SuscripcionId suscripcionId = SuscripcionId.builder()
                .clienteId(suscripcionDto.getCliente().getId())
                .fondoId(suscripcionDto.getFondo().getId())
                .build();

        this.obtenerSuscripcionPorId(suscripcionId);

        suscripcionRepository.deleteById(suscripcionId);
    }

    private void crearTransaccion(SuscripcionDto suscripcionDto, EstadoTransaccionEnum estadoTransaccion) {

        TransaccionDto transaccionDto = TransaccionDto.builder()
                .cliente(suscripcionDto.getCliente())
                .fondo(suscripcionDto.getFondo())
                .estadoTransaccion(EstadoTransaccionDto.builder()
                        .id(estadoTransaccion.getIdEstado())
                        .build())
                .fecha(LocalDateTime.now())
                .build();

        transaccionService.crearTransaccion(transaccionDto);
    }
}

