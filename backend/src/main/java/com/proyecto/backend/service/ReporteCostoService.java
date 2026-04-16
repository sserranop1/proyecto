package com.proyecto.backend.service;

import com.proyecto.backend.dto.RegistrarCostoRequest;
import com.proyecto.backend.model.CuentaCloud;
import com.proyecto.backend.model.DatoCostoMensual;
import com.proyecto.backend.repository.DatoCostoMensualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReporteCostoService {

    private final DatoCostoMensualRepository datoCostoMensualRepository;
    private final CuentaCloudService cuentaCloudService;

    public DatoCostoMensual registrarCosto(Long cuentaId, RegistrarCostoRequest request) {
        CuentaCloud cuentaCloud = cuentaCloudService.buscarPorId(cuentaId);

        DatoCostoMensual dato = DatoCostoMensual.builder()
                .periodo(request.periodo())
                .servicio(request.servicio())
                .proyecto(request.proyecto())
                .costo(request.costo())
                .moneda(request.moneda())
                .uso(request.uso())
                .unidadUso(request.unidadUso())
                .cuentaCloud(cuentaCloud)
                .importedAt(LocalDateTime.now())
                .build();

        return datoCostoMensualRepository.save(dato);
    }

    @Transactional(readOnly = true)
    public List<DatoCostoMensual> listarCostosPorPeriodo(Long cuentaId, String periodo) {
        return datoCostoMensualRepository.findByCuentaCloudIdAndPeriodo(cuentaId, periodo);
    }

    @Transactional(readOnly = true)
    public BigDecimal obtenerCostoTotal(Long cuentaId, String periodo) {
        return datoCostoMensualRepository.sumarCostoTotalPorCuentaYPeriodo(cuentaId, periodo);
    }
}