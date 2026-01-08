package com.alura.reservas.reserva.service;

import com.alura.reservas.reserva.domain.Sala;
import com.alura.reservas.reserva.domain.Usuario;
import com.alura.reservas.reserva.dto.*;
import com.alura.reservas.reserva.infra.exception.ValidacaoException;
import com.alura.reservas.reserva.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario create(UsuarioRequestDTO dto) {
        if(usuarioRepository.existsByEmail(dto.email()))
            throw new ValidacaoException("E-mail já cadastrado!");

        return usuarioRepository.save(new Usuario(dto));
    }

    public Page<UsuarioResponseDTO> findAll(Pageable paginacao) {

        return usuarioRepository.findAll(paginacao).map(UsuarioResponseDTO::new);
    }

    public UsuarioResponseDTO findUsuario(Long id) {

        var usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return new UsuarioResponseDTO(usuario);
    }

    public void delete(Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado")
        );

        usuarioRepository.deleteById(usuario.getId());
    }

    public void toAlter(UsuarioUpdateDto dto) {
        Usuario usuario = usuarioRepository.getReferenceById(dto.id());
        if(dto.email() != null)
            usuario.setEmail(dto.email());

        if (dto.nome() != null){
            usuario.setNome(dto.nome());
        }
    }

    public Page<UsuarioResponseDTO> listAll(Pageable pageable) {

        return usuarioRepository.findAll(pageable).map(UsuarioResponseDTO::new);
    }

    public UsuarioResponseDTO listAll(Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow(()-> new  EntityNotFoundException("Usuário não encontrado"));

        return new UsuarioResponseDTO(usuario);
    }
}
