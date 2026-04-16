package com.proyecto.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "datos_costos_mensuales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatoCostoMensual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String periodo; // Ej: 2026-04

    @Column(length = 120)
    private String servicio;

    @Column(length = 120)
    private String proyecto;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal costo;

    @Column(nullable = false, length = 10)
    private String moneda;

    @Column(precision = 16, scale = 4)
    private BigDecimal uso;

    @Column(length = 20)
    private String unidadUso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuenta_cloud_id")
    private CuentaCloud cuentaCloud;

    @Column(nullable = false)
    private LocalDateTime importedAt;
}