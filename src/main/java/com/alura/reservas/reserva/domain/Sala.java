package com.alura.reservas.reserva.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "salas")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome legível da sala.
     * Ex.: "Sala de Reunião 01", "Auditório"
     */
    @Column(nullable = false, unique = true)
    private String nome;

    /**
     * Capacidade máxima de pessoas.
     * Deve ser sempre > 0 (regra de domínio).
     */
    @Column(nullable = false)
    private Integer capacidade;

    /**
     * Indica se a sala pode ser reservada.
     * Salas inativas não devem aceitar novas reservas.
     */
    @Column(nullable = false)
    private boolean ativa = true;

    /**
     * Relacionamento com reservas.
     * mappedBy indica que Reserva é a dona da relação.
     */
    @OneToMany(mappedBy = "sala")
    private List<Reserva> reservas = new ArrayList<>();

    public Sala(String nome, Integer capacidade) {
        if (capacidade == null || capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade da sala deve ser maior que zero");
        }
        this.nome = nome;
        this.capacidade = capacidade;
        this.ativa = true;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void desativar() {
        this.ativa = false;
    }
}
