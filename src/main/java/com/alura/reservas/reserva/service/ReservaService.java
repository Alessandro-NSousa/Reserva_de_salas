package com.alura.reservas.reserva.service;

import com.alura.reservas.reserva.domain.Reserva;
import com.alura.reservas.reserva.domain.Sala;
import com.alura.reservas.reserva.domain.Usuario;
import com.alura.reservas.reserva.dto.ReservaRequestDTO;
import com.alura.reservas.reserva.dto.SalaRequestDTO;
import com.alura.reservas.reserva.dto.ReservaResponseDTO;
import com.alura.reservas.reserva.dto.SalaUpdateDTO;
import com.alura.reservas.reserva.infra.exception.ValidacaoException;
import com.alura.reservas.reserva.repository.ReservaRepository;
import com.alura.reservas.reserva.repository.SalaRepository;
import com.alura.reservas.reserva.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReservaService {
    
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SalaRepository salaRepository;

    public ReservaResponseDTO criarReserva(ReservaRequestDTO request) {

        Sala sala = salaRepository.findById(request.salaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Criação já valida datas e sala ativa
        Reserva novaReserva = new Reserva(
                sala,
                usuario,
                request.inicio(),
                request.fim()
        );

        validarConflito(novaReserva);

        Reserva salva = reservaRepository.save(novaReserva);

        return new ReservaResponseDTO(salva);
    }

    private void validarConflito(Reserva novaReserva) {

        List<Reserva> reservasExistentes =
                reservaRepository.buscarReservasAtivasDaSala(
                        novaReserva.getSala().getId()
                );

        boolean existeConflito = reservasExistentes.stream()
                .anyMatch(reserva -> reserva.conflitaCom(novaReserva));

        if (existeConflito) {
            throw new ValidacaoException("Sala já reservada nesse horário");
        }
    }

    public void cancelarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        reserva.cancelar();
    }
}
