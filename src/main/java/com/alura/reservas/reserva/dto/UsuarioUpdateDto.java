package com.alura.reservas.reserva.dto;

public record UsuarioUpdateDto(
        Long id,
        String nome,
        String email
) {
}
