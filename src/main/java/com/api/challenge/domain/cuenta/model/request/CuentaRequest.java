package com.api.challenge.domain.cuenta.model.request;

import com.api.challenge.domain.cuenta.model.enumerated.Divisa;
import com.api.challenge.domain.cuenta.model.enumerated.TipoCuenta;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaRequest {

    @NotBlank(message = "El campo numero de cuenta es obligatorio")
    @NotNull(message = "El campo numero de cuenta es obligatorio")
    private String numeroCuenta;

    @NotNull(message = "El campo numero de cuenta es obligatorio")
    @JsonProperty(value = "divisa")
    @Enumerated(EnumType.STRING)
    private Divisa divisa;

    @NotNull(message = "El campo tipo de cuenta es obligatorio")
    @JsonProperty(value = "tipo_cuenta")
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El campo numero de cuenta es obligatorio")
    private BigDecimal saldoInicial;

    @NotNull(message = "El campo estado de cuenta es obligatorio")
    @Column(name = "estado")
    private Boolean estado;

}
