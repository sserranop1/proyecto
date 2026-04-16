package com.proyecto.backend.dto;

import com.proyecto.backend.model.RoleType;

public record SyncUsuarioRequest(
        String auth0Id,
        String email,
        String nombreCompleto,
        RoleType rol
) {
}