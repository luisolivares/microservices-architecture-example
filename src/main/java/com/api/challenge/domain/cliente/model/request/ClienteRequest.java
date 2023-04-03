package com.api.challenge.domain.cliente.model.request;

import com.api.challenge.domain.cliente.model.enumerated.Genero;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.utils.ChallengeUtils;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteRequest {

    @NotBlank(message = "El campo nombre no debe estar vacio")
    @NotNull(message = "El campo nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El campo apellidos no debe estar vacio")
    @NotNull(message = "El campo apellidos es obligatorio")
    private String apellidos;

    @NotBlank(message = "El campo telefono no debe estar vacio")
    @NotNull(message = "El campo telefono es obligatorio")
    @Pattern(regexp= ChallengeUtils.PATTER_TELEPHONE, message = "En el campo telefono debe ingresar un valor numerico")
    private String telefono;

    @NotBlank(message = "El campo fechaNacimiento no debe estar vacio")
    @NotNull(message = "El campo fechaNacimiento es obligatorio")
    private String fechaNacimiento;

    @NotBlank(message = "El campo email no debe estar vacio")
    @NotNull(message = "El campo email es obligatorio")
    @Email(message = "El formato del email es inválido")
    private String email;

    @NotNull(message = "El campo estado no debe ser nulo")
    @Enumerated(EnumType.STRING)
    private Boolean estado;

    @NotNull(message = "El campo genero no debe estar nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @NotNull(message = "El campo tipoDocumento no debe estar nulo")
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El campo documento no debe estar vacio")
    @NotNull(message = "El campo documento no debe estar nulo")
    private String documento;

    @NotBlank(message = "El campo contraseña es obligatorio")
    @NotNull(message = "El campo contraseña es obligatorio")
    private String contraseña;

}
