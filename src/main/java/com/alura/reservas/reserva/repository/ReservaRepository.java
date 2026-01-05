package com.alura.reservas.reserva.repository;

import com.alura.reservas.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
