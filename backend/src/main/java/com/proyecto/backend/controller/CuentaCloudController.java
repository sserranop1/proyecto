package com.proyecto.backend.controller;

import com.proyecto.backend.dto.RegistrarCuentaRequest;
import com.proyecto.backend.model.CuentaCloud;
import com.proyecto.backend.service.CuentaCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaCloudController {

    private final CuentaCloudService cuentaCloudService;

    @PostMapping
    public CuentaCloud registrarCuenta(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody RegistrarCuentaRequest request
    ) {
        return cuentaCloudService.registrarMiCuenta(jwt.getSubject(), request);
    }

    @GetMapping
    public List<CuentaCloud> listarMisCuentas(@AuthenticationPrincipal Jwt jwt) {
        return cuentaCloudService.listarMisCuentas(jwt.getSubject());
    }

    @GetMapping("/{cuentaId}")
    public CuentaCloud buscarPorId(@PathVariable Long cuentaId) {
        return cuentaCloudService.buscarPorId(cuentaId);
    }
}