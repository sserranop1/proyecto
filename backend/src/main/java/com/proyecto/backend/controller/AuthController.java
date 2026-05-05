package com.proyecto.backend.controller;

import com.proyecto.backend.model.Usuario;
import com.proyecto.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/sync")
    public Usuario syncUsuario(@AuthenticationPrincipal Jwt jwt) {
        return usuarioService.syncDesdeJwt(jwt);
    }

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "sub", jwt.getSubject(),
                "email", jwt.getClaimAsString("email"),
                "name", jwt.getClaimAsString("name"),
                "scope", jwt.getClaimAsString("scope")
        );
    }
}