package com.alura.reservas.reserva.dto;

public record SalaPutRequestDTO(
        Long id,
        String nome,
        Integer capacidade
) {
}
