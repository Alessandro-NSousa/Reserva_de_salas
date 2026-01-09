package com.alura.reservas.reserva.dto;

import com.alura.reservas.reserva.domain.Reserva;
import com.alura.reservas.reserva.domain.Sala;
import com.alura.reservas.reserva.domain.Usuario;
import com.alura.reservas.reserva.domain.enumeration.StatusReserva;

import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        String sala,
        String usuario,
        LocalDateTime inicio,
        LocalDateTime fim,
        StatusReserva status
) {
    public ReservaResponseDTO(Reserva dados) {
        this(dados.getId(), dados.getSala().getNome(),dados.getUsuario().getNome(), dados.getInicio(), dados.getFim(), dados.getStatus());
    }
}
