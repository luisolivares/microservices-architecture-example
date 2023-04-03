package com.api.challenge.domain.cuenta.model.entity;

import com.api.challenge.domain.cliente.model.entity.Cliente;
import com.api.challenge.domain.cuenta.model.enumerated.Divisa;
import com.api.challenge.domain.cuenta.model.enumerated.TipoCuenta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Cuenta")
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private int idCuenta;

    @NotBlank(message = "El campo numero de cuenta es obligatorio")
    @NotNull(message = "El campo numero de cuenta es obligatorio")
    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @NotNull(message = "El campo numero de cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "divisa")
    private Divisa divisa;

    @NotNull(message = "El campo tipoCuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El campo numero de cuenta es obligatorio")
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    @NotNull(message = "El campo estado de cuenta es obligatorio")
    @Column(name = "estado")
    private Boolean estado;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
