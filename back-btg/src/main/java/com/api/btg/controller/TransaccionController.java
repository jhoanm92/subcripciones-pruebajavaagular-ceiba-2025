package com.api.btg.controller;

import com.api.btg.dto.TransaccionDto;
import com.api.btg.service.TransaccionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService suscripcionService;

    @GetMapping("/cliente-id/{id}")
    public ResponseEntity<List<TransaccionDto>> obtenerTransaccionesPorCliente(@PathVariable Integer id) {

        log.info("REST - obtenerTransaccionesPorCliente del clienteid {}", id);

        return ResponseEntity.ok(suscripcionService.obtenerTransaccionesPorCliente(id));
    }
}
