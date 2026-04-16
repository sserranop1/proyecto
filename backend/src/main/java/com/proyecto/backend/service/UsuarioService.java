package com.proyecto.backend.service;

import com.proyecto.backend.dto.SyncUsuarioRequest;
import com.proyecto.backend.model.Usuario;
import com.proyecto.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario syncUsuario(SyncUsuarioRequest request) {
        return usuarioRepository.findByAuth0Id(request.auth0Id())
                .map(usuario -> {
                    usuario.setEmail(request.email());
                    usuario.setNombreCompleto(request.nombreCompleto());
                    usuario.setRol(request.rol());
                    usuario.setActivo(true);
                    return usuarioRepository.save(usuario);
                })
                .orElseGet(() -> usuarioRepository.save(
                        Usuario.builder()
                                .auth0Id(request.auth0Id())
                                .email(request.email())
                                .nombreCompleto(request.nombreCompleto())
                                .rol(request.rol())
                                .activo(true)
                                .createdAt(LocalDateTime.now())
                                .build()
                ));
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorAuth0Id(String auth0Id) {
        return usuarioRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));
    }
}