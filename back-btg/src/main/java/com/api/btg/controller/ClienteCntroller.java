package com.api.btg.controller;

import com.api.btg.dto.ClienteDto;
import com.api.btg.service.ClienteService;
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
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteCntroller {

    private final ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<List<ClienteDto>> obtenerCLientes() {

        log.info("REST - obtenerCLientes");

        return ResponseEntity.ok(clienteService.obtenerCLientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerCLientePorId(@PathVariable Integer id) {

        log.info("REST - obtenerCLientePorId");

        return ResponseEntity.ok(clienteService.obtenerCLientePorId(id));
    }
}
