package com.alura.reservas.reserva.service;

import com.alura.reservas.reserva.domain.Sala;
import com.alura.reservas.reserva.dto.SalaRequestDTO;
import com.alura.reservas.reserva.dto.SalaResponseDTO;
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

    public void toAlter(AprovacaoAdocaoDto dto) {
        Adocao adocao = repository.getReferenceById(dto.idAdocao());
        adocao.marcarComoAprovada();

        emailService.enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.");
    }
}
