package com.alura.reservas.reserva.dto;

public record SalaUpdateDTO(
        String nome,
        Integer capacidade,
        Boolean ativa

) {
}
