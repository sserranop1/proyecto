package com.proyecto.backend.service;

import com.proyecto.backend.model.RoleType;
import com.proyecto.backend.model.Usuario;
import com.proyecto.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
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

    public Usuario syncDesdeJwt(Jwt jwt) {
        String auth0Id = jwt.getSubject();
        String email = firstNonBlank(
                jwt.getClaimAsString("email"),
                auth0Id + "@placeholder.local"
        );
        String nombre = firstNonBlank(
                jwt.getClaimAsString("name"),
                jwt.getClaimAsString("nickname"),
                email
        );

        return usuarioRepository.findByAuth0Id(auth0Id)
                .map(usuario -> {
                    usuario.setEmail(email);
                    usuario.setNombreCompleto(nombre);
                    usuario.setActivo(true);
                    return usuarioRepository.save(usuario);
                })
                .orElseGet(() -> usuarioRepository.save(
                        Usuario.builder()
                                .auth0Id(auth0Id)
                                .email(email)
                                .nombreCompleto(nombre)
                                .rol(RoleType.VIEWER)
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

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return "Usuario";
    }
}