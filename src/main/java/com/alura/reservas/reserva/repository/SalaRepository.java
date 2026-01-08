package com.alura.reservas.reserva.repository;

import com.alura.reservas.reserva.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    boolean existsByNomeAndIdNot(String nome, Long id);
}
