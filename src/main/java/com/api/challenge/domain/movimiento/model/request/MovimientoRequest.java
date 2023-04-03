package com.api.challenge.domain.movimiento.model.request;

import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.movimiento.model.enumerated.TipoMovimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoRequest {

    @NotNull(message = "El campo saldo no debe ser nulo")
    private BigDecimal saldoMovimiento;

    @NotNull(message = "El campo fechaTransaccion no debe estar nulo")
    private Date fechaTransaccion;

    @NotNull(message = "El campo tipoMovimiento no debe ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;

    @NotNull(message = "El campo tipoDocumento no debe ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El campo documento de cuenta no debe ser vacio")
    @NotNull(message = "El campo documento de cuenta no debe ser nulo")
    private String documento;

    @NotBlank(message = "El campo numero de cuenta no debe ser vacio")
    @NotNull(message = "El campo numero de cuenta no debe ser nulo")
    private String numeroCuenta;

}
