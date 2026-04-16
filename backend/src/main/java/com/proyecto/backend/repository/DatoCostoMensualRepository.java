package com.proyecto.backend.repository;

import com.proyecto.backend.model.DatoCostoMensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DatoCostoMensualRepository extends JpaRepository<DatoCostoMensual, Long> {

    List<DatoCostoMensual> findByCuentaCloudIdAndPeriodo(Long cuentaId, String periodo);

    @Query("""
           select coalesce(sum(d.costo), 0)
           from DatoCostoMensual d
           where d.cuentaCloud.id = :cuentaId
             and d.periodo = :periodo
           """)
    BigDecimal sumarCostoTotalPorCuentaYPeriodo(Long cuentaId, String periodo);
}