package com.api.challenge.domain.cliente.model.entity;

import com.api.challenge.domain.cliente.model.enumerated.Genero;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.cuenta.model.entity.Cuenta;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Cliente")
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int idCliente;

    @NotBlank(message = "El campo nombre no debe estar vacio")
    @NotNull(message = "El campo nombre no debe estar vacio")
    @Column(name = "nombres", nullable = false)
    private String nombres;

    @NotBlank(message = "El campo apellidos no debe estar vacio")
    @NotNull(message = "El campo apellidos no debe estar vacio")
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @NotBlank(message = "El campo telefono no debe estar vacio")
    @NotNull(message = "El campo telefono no debe estar nulo")
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull(message = "El campo fechaNacimiento no debe estar nulo")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @NotBlank(message = "El campo email no debe estar vacio")
    @NotNull(message = "El campo email no debe ser nulo")
    @Email(message = "El formato del email es inválido")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull(message = "El campo estado no debe ser nulo")
    @Column(name = "estado", nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean estado;

    @NotNull(message = "El campo genero no debe ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @NotNull(message = "El campo tipoDocumento no debe ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoDocumento", nullable = false)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El campo documento no debe estar vacio")
    @NotNull(message = "El campo documento no debe ser nulo")
    @Column(name = "documento", unique = true, nullable = false)
    private String documento;

    @NotBlank(message = "El campo contraseña no debe ser vacio")
    @NotNull(message = "El campo contraseña no debe ser nulo")
    @Column(name = "contraseña", nullable = false)
    private String contraseña;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "fecha_creacion")
    private Date fechaCreacion = new Date();

    @OneToMany(mappedBy="cliente", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Set<Cuenta> cuentas;

}
