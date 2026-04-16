package com.proyecto.backend.controller;

import com.proyecto.backend.dto.RegistrarCostoRequest;
import com.proyecto.backend.model.DatoCostoMensual;
import com.proyecto.backend.service.ReporteCostoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteCostoController {

    private final ReporteCostoService reporteCostoService;

    @PostMapping("/cuentas/{cuentaId}/costos")
    public DatoCostoMensual registrarCosto(
            @PathVariable Long cuentaId,
            @RequestBody RegistrarCostoRequest request
    ) {
        return reporteCostoService.registrarCosto(cuentaId, request);
    }

    @GetMapping("/cuentas/{cuentaId}/costos")
    public List<DatoCostoMensual> listarCostosPorPeriodo(
            @PathVariable Long cuentaId,
            @RequestParam String periodo
    ) {
        return reporteCostoService.listarCostosPorPeriodo(cuentaId, periodo);
    }

    @GetMapping("/cuentas/{cuentaId}/total")
    public BigDecimal obtenerCostoTotal(
            @PathVariable Long cuentaId,
            @RequestParam String periodo
    ) {
        return reporteCostoService.obtenerCostoTotal(cuentaId, periodo);
    }
}