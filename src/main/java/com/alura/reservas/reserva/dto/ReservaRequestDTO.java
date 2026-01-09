package com.alura.reservas.reserva.dto;

import java.time.LocalDateTime;

public record ReservaRequestDTO(
        Long salaId,
        Long usuarioId,
        LocalDateTime inicio,
        LocalDateTime fim
) {
}
