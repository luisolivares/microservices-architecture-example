package com.api.challenge.domain.movimiento.model.dto;

import com.api.challenge.domain.cuenta.model.enumerated.Divisa;
import com.api.challenge.domain.cuenta.model.enumerated.TipoCuenta;
import com.api.challenge.domain.movimiento.model.enumerated.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class MovimientoDTO {
    private Date fecha;
    private String cliente;
    private TipoCuenta tipoCuenta;
    private Divisa divisa;
    private String numeroCuenta;
    private TipoMovimiento tipoMovimiento;
    private BigDecimal saldoInicial;
    private BigDecimal saldoMovimiento;
    private BigDecimal saldoDisponible;
}
