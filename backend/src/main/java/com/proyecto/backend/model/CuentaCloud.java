package com.proyecto.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "cuentas_cloud",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"proveedor", "externalAccountId"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaCloud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProveedorCloud proveedor;

    @Column(nullable = false, length = 120)
    private String externalAccountId;

    @Column(length = 50)
    private String region;

    @Column(length = 255)
    private String secretReference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private EstadoCuentaCloud estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastValidatedAt;
}