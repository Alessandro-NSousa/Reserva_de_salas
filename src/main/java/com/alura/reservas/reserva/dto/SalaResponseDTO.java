package com.alura.reservas.reserva.dto;

import com.alura.reservas.reserva.domain.Sala;

public record SalaResponseDTO(
        Long id,
        String nome,
        Integer capacidade,
        Boolean ativo
) {
    public SalaResponseDTO(Sala dados){
        this(dados.getId(), dados.getNome(), dados.getCapacidade(), dados.isAtiva());
    }
}
