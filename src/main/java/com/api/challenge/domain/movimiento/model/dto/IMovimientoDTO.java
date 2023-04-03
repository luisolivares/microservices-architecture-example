package com.api.challenge.domain.movimiento.model.dto;

import com.api.challenge.domain.cuenta.model.enumerated.TipoCuenta;
import com.api.challenge.domain.movimiento.model.enumerated.TipoMovimiento;

import java.math.BigDecimal;
import java.util.Date;

public interface IMovimientoDTO {

    Date getFecha();

    String getCliente();

    TipoCuenta getTipoCuenta();

    String getNumeroCuenta();

    TipoMovimiento getTipoMovimiento();

    BigDecimal getSaldoInicial();

    BigDecimal getSaldoMovimiento();

    BigDecimal getSaldoDisponible();

}
