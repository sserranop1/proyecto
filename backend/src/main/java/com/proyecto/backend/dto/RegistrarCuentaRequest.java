package com.proyecto.backend.dto;

import com.proyecto.backend.model.ProveedorCloud;

public record RegistrarCuentaRequest(
        String nombre,
        ProveedorCloud proveedor,
        String externalAccountId,
        String region,
        String secretReference
) {
}