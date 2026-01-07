package com.alura.reservas.reserva.controller;

import com.alura.reservas.reserva.dto.SalaRequestDTO;
import com.alura.reservas.reserva.dto.SalaResponseDTO;
import com.alura.reservas.reserva.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<SalaResponseDTO> createSala(@RequestBody SalaRequestDTO dto){
        var sala = salaService.create(dto);

        return ResponseEntity.ok(new SalaResponseDTO(sala));
    }

    @GetMapping
    public Page<SalaResponseDTO> listAll(@PageableDefault Pageable paginacao) {
        return salaService.findAll(paginacao);
    }

    @GetMapping("/{id}")
    public SalaResponseDTO listAll(@PathVariable Long id) {
        return salaService.findSala(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        salaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovacaoAdocaoDto dto) {
        this.adocaoService.aprovar(dto);
        return ResponseEntity.ok().build();
    }
}
