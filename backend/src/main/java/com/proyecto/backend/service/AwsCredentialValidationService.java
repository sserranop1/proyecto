package com.proyecto.backend.service;

import com.proyecto.backend.dto.RegistrarCuentaRequest;
import com.proyecto.backend.model.ProveedorCloud;
import org.springframework.stereotype.Service;

@Service
public class AwsCredentialValidationService {

    public boolean validarCredenciales(RegistrarCuentaRequest request) {
        if (request.proveedor() != ProveedorCloud.AWS) {
            return true;
        }

        // TODO: aquí conectas la validación real con AWS STS / IAM / SDK
        // Por ahora, simulamos que si hay secretReference, la validación pasa.
        return request.secretReference() != null && !request.secretReference().isBlank();
    }
}