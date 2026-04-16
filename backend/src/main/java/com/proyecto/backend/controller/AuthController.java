package com.proyecto.backend.controller;

import com.proyecto.backend.dto.SyncUsuarioRequest;
import com.proyecto.backend.model.Usuario;
import com.proyecto.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/sync")
    public Usuario syncUsuario(@RequestBody SyncUsuarioRequest request) {
        return usuarioService.syncUsuario(request);
    }

    @GetMapping("/me/{auth0Id}")
    public Usuario obtenerUsuario(@PathVariable String auth0Id) {
        return usuarioService.buscarPorAuth0Id(auth0Id);
    }
}