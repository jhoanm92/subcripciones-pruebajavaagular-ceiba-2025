package com.api.btg.controller;

import com.api.btg.dto.FondoDto;
import com.api.btg.dto.TransaccionDto;
import com.api.btg.service.FondoService;
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
@RequestMapping("/fondos")
@RequiredArgsConstructor
public class FondosController {

    private final FondoService fondoService;

    @GetMapping()
    public ResponseEntity<List<FondoDto>> obtenerFondos() {

        log.info("REST - obtenerFondos");

        return ResponseEntity.ok(fondoService.obtenerFondos());
    }
}
