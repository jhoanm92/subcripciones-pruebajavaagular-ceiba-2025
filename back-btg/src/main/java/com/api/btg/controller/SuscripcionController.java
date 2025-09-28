package com.api.btg.controller;

import com.api.btg.dto.FondoDto;
import com.api.btg.dto.SuscripcionDto;
import com.api.btg.dto.request.SuscripcionFondoRequest;
import com.api.btg.service.SuscripcionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    @PostMapping()
    public ResponseEntity<SuscripcionDto> manejarSuscripcion(@RequestBody SuscripcionFondoRequest suscripcionFondoRequest) {

        log.info("REST - manejarSuscripcion {}", suscripcionFondoRequest);

        return ResponseEntity.ok(suscripcionService.manejarSuscripcion(suscripcionFondoRequest));
    }

    @GetMapping("/cliente-id/{id}")
    public ResponseEntity<List<FondoDto>> obtenerTransaccionesPorCliente(@PathVariable Integer id) {

        log.info("REST - obtenerTransaccionesPorCliente del clienteid {}", id);

        return ResponseEntity.ok(suscripcionService.obtenerSuscripcionesPorIdCliente(id)
                .stream().map(SuscripcionDto::getFondo)
                .toList());
    }
}
