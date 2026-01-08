package com.alura.reservas.reserva.service;

import com.alura.reservas.reserva.domain.Sala;
import com.alura.reservas.reserva.dto.SalaRequestDTO;
import com.alura.reservas.reserva.dto.SalaResponseDTO;
import com.alura.reservas.reserva.dto.SalaUpdateDTO;
import com.alura.reservas.reserva.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    public Sala create(SalaRequestDTO dto) {
        Sala sala = new Sala(dto);

        return salaRepository.save(sala);

    }

    public Page<SalaResponseDTO> findAll(Pageable paginacao) {

        return salaRepository.findAll(paginacao).map(SalaResponseDTO::new);
    }

    public SalaResponseDTO findSala(Long id) {

        var sala = salaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        return new SalaResponseDTO(sala);
    }

    public void delete(Long id) {
        var sala = salaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Sala não encontrada")
        );

        salaRepository.deleteById(sala.getId());
    }

    public Sala toAlter(Long id, SalaUpdateDTO dto) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        if (dto.nome() != null && !dto.nome().equals(sala.getNome())) {
            if (salaRepository.existsByNomeAndIdNot(dto.nome(), id)) {
                throw new IllegalArgumentException("Já existe uma sala com esse nome");
            }
            sala.setNome(dto.nome());
        }

        if (dto.capacidade() != null) {
            if (dto.capacidade() <= 0) {
                throw new IllegalArgumentException("Capacidade deve ser maior que zero");
            }
            sala.setCapacidade(dto.capacidade());
        }

        if (dto.ativa() != null) {
            sala.setAtiva(dto.ativa());
        }

        return salaRepository.save(sala);
    }
}
