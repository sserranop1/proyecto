package com.proyecto.backend.dto;

import java.math.BigDecimal;

public record RegistrarCostoRequest(
        String periodo,
        String servicio,
        String proyecto,
        BigDecimal costo,
        String moneda,
        BigDecimal uso,
        String unidadUso
) {
}