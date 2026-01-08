package com.alura.reservas.reserva.dto;

import com.alura.reservas.reserva.domain.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email
) {
    public UsuarioResponseDTO(Usuario dados){
        this(dados.getId(),dados.getNome(),dados.getEmail());
    }
}
