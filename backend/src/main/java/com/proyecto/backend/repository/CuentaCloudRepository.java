package com.proyecto.backend.repository;

import com.proyecto.backend.model.CuentaCloud;
import com.proyecto.backend.model.ProveedorCloud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaCloudRepository extends JpaRepository<CuentaCloud, Long> {

    List<CuentaCloud> findByUsuarioId(Long usuarioId);

    Optional<CuentaCloud> findByProveedorAndExternalAccountId(
            ProveedorCloud proveedor,
            String externalAccountId
    );
}