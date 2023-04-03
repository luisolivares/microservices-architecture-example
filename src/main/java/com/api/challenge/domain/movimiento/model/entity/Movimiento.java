package com.api.challenge.domain.movimiento.model.entity;

import com.api.challenge.domain.cuenta.model.entity.Cuenta;
import com.api.challenge.domain.movimiento.model.enumerated.TipoMovimiento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimiento")
@Entity(name = "Movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private int idMovimiento;

    @NotNull(message = "El campo saldoInicial no debe ser nulo")
    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;

    @NotNull(message = "El campo saldoMovimiento no debe ser nulo")
    @Column(name = "saldo_movimiento", nullable = false)
    private BigDecimal saldoMovimiento;

    @NotNull(message = "El campo saldoDisponible no debe ser nulo")
    @Column(name = "saldoDisponible", nullable = false)
    private BigDecimal saldoDisponible;

    @NotNull(message = "El campo fechaTransaccion no debe estar nulo")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_transaccion", nullable = false)
    private Date fechaTransaccion = new Date();

    @NotNull(message = "El campo tipoMovimiento no debe ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

}