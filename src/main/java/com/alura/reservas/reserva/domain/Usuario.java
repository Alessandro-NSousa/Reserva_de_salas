package com.alura.reservas.reserva.domain;

import com.alura.reservas.reserva.dto.UsuarioRequestDTO;
import com.alura.reservas.reserva.dto.UsuarioUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "usuarios")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do usuário que realiza a reserva.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Email usado como identificador de contato.
     * Pode futuramente virar login.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Reservas criadas pelo usuário.
     */
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas = new ArrayList<>();


    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Usuario(UsuarioRequestDTO dto) {
        this.nome = dto.nome();
        this.email = dto.email();
    }

    public void updateData(UsuarioUpdateDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
    }
}
