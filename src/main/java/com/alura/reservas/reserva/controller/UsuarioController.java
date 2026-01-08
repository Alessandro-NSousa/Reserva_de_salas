package com.alura.reservas.reserva.controller;

import com.alura.reservas.reserva.dto.SalaResponseDTO;
import com.alura.reservas.reserva.dto.UsuarioRequestDTO;
import com.alura.reservas.reserva.dto.UsuarioResponseDTO;
import com.alura.reservas.reserva.dto.UsuarioUpdateDto;
import com.alura.reservas.reserva.infra.exception.ValidacaoException;
import com.alura.reservas.reserva.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioResponseDTO>  create(@Valid @RequestBody UsuarioRequestDTO dto) {
        var usuario = service.create(dto);
        return ResponseEntity.ok().body(new UsuarioResponseDTO(usuario));
    }

    @GetMapping
    public Page<UsuarioResponseDTO> listAll(@PageableDefault Pageable pageable) {
        return service.listAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> listAll(@PathVariable Long id) {
        var usuario = service.findUsuario(id);

        return ResponseEntity.ok().body(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> toAlter(@RequestBody @Valid UsuarioUpdateDto dto) {
        try {
            service.toAlter(dto);
            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
