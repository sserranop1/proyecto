package com.proyecto.backend.controller;

import com.proyecto.backend.dto.RegistrarCuentaRequest;
import com.proyecto.backend.model.CuentaCloud;
import com.proyecto.backend.service.CuentaCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaCloudController {

    private final CuentaCloudService cuentaCloudService;

    @PostMapping("/usuario/{usuarioId}")
    public CuentaCloud registrarCuenta(
            @PathVariable Long usuarioId,
            @RequestBody RegistrarCuentaRequest request
    ) {
        return cuentaCloudService.registrarCuenta(usuarioId, request);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<CuentaCloud> listarPorUsuario(@PathVariable Long usuarioId) {
        return cuentaCloudService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{cuentaId}")
    public CuentaCloud buscarPorId(@PathVariable Long cuentaId) {
        return cuentaCloudService.buscarPorId(cuentaId);
    }
}