package com.alura.reservas.reserva.domain;

import com.alura.reservas.reserva.domain.enumeration.StatusReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "reservas")
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Sala reservada.
     * Uma reserva SEMPRE pertence a uma sala.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    /**
     * Usuário responsável pela reserva.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Data e hora de início da reserva.
     * Intervalo é semiaberto: [inicio, fim)
     */
    @Column(nullable = false)
    private LocalDateTime inicio;

    /**
     * Data e hora de fim da reserva.
     * Não é inclusivo.
     */
    @Column(nullable = false)
    private LocalDateTime fim;

    /**
     * Status da reserva.
     * Reservas CANCELADAS não entram em checagem de conflito.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva status;

    protected Reserva() {}

    public Reserva(Sala sala, Usuario usuario,
                   LocalDateTime inicio, LocalDateTime fim) {

        validarDatas(inicio, fim);

        if (!sala.isAtiva()) {
            throw new IllegalStateException("Não é possível reservar uma sala inativa");
        }

        this.sala = sala;
        this.usuario = usuario;
        this.inicio = inicio;
        this.fim = fim;
        this.status = StatusReserva.ATIVA;
    }

    /**
     * Regra: início deve ser anterior ao fim.
     */
    private void validarDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        if (!inicio.isBefore(fim)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
        }
    }

    /**
     * Cancela a reserva.
     * Reservas canceladas não geram conflito.
     */
    public void cancelar() {
        this.status = StatusReserva.CANCELADA;
    }

    /**
     * Verifica se esta reserva conflita com outra,
     * considerando intervalo semiaberto [inicio, fim).
     */
    public boolean conflitaCom(Reserva outra) {
        if (this.status == StatusReserva.CANCELADA ||
                outra.status == StatusReserva.CANCELADA) {
            return false;
        }

        return this.inicio.isBefore(outra.fim)
                && this.fim.isAfter(outra.inicio);
    }

}
