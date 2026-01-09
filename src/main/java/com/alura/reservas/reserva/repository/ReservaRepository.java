package com.alura.reservas.reserva.repository;

import com.alura.reservas.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Retorna apenas reservas ATIVAS da sala,
     * pois canceladas não entram na checagem de conflito.
     */
    @Query("""
        select r
        from Reserva r
        where r.sala.id = :salaId
          and r.status = 'ATIVA'
    """)
    List<Reserva> buscarReservasAtivasDaSala(@Param("salaId") Long salaId);
}
