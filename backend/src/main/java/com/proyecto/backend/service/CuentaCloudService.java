package com.proyecto.backend.service;

import com.proyecto.backend.dto.RegistrarCuentaRequest;
import com.proyecto.backend.model.CuentaCloud;
import com.proyecto.backend.model.EstadoCuentaCloud;
import com.proyecto.backend.model.Usuario;
import com.proyecto.backend.repository.CuentaCloudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class CuentaCloudService {

    private final CuentaCloudRepository cuentaCloudRepository;
    private final UsuarioService usuarioService;
    private final AwsCredentialValidationService awsCredentialValidationService;

    public CuentaCloud registrarCuenta(Long usuarioId, RegistrarCuentaRequest request) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        cuentaCloudRepository.findByProveedorAndExternalAccountId(
                request.proveedor(),
                request.externalAccountId()
        ).ifPresent(cuenta -> {
            throw new ResponseStatusException(CONFLICT, "La cuenta cloud ya existe");
        });

        CuentaCloud cuenta = CuentaCloud.builder()
                .nombre(request.nombre())
                .proveedor(request.proveedor())
                .externalAccountId(request.externalAccountId())
                .region(request.region())
                .secretReference(request.secretReference())
                .estado(EstadoCuentaCloud.PENDING_VALIDATION)
                .usuario(usuario)
                .createdAt(LocalDateTime.now())
                .build();

        boolean credencialesValidas = awsCredentialValidationService.validarCredenciales(request);

        if (credencialesValidas) {
            cuenta.setEstado(EstadoCuentaCloud.ACTIVE);
            cuenta.setLastValidatedAt(LocalDateTime.now());
        } else {
            cuenta.setEstado(EstadoCuentaCloud.ERROR);
        }

        return cuentaCloudRepository.save(cuenta);
    }

    @Transactional(readOnly = true)
    public List<CuentaCloud> listarPorUsuario(Long usuarioId) {
        return cuentaCloudRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public CuentaCloud buscarPorId(Long cuentaId) {
        return cuentaCloudRepository.findById(cuentaId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cuenta cloud no encontrada"));
    }
}