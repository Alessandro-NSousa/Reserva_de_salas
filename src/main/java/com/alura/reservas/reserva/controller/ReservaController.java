package com.alura.reservas.reserva.controller;

import com.alura.reservas.reserva.dto.*;
import com.alura.reservas.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> cadastrar(
            @RequestBody ReservaRequestDTO request) {

        ReservaResponseDTO response = service.criarReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
