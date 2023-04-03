package com.api.challenge.domain.cuenta.controller;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cliente.model.entity.Cliente;
import com.api.challenge.domain.cuenta.model.request.CuentaRequest;
import com.api.challenge.domain.cuenta.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/v1/cuenta")
@RestController()
public class CuentaController {

    private final CuentaService cuentaService;

    @Operation(summary = "Crear Cuenta Bancaria", description = "Operación donde se crea una Cuenta Bancaria en Base de Datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creación de una cuenta bancaria", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ResponseStatusException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseStatusException.class)))
    }
    )
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO> create(@Valid @RequestBody CuentaRequest request) {
        return new ResponseEntity<>(cuentaService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Modificar Cuenta Bancaria", description = "Operación donde se modifica una cuenta bancaria en Base de Datos por su numero de cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ResponseStatusException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseStatusException.class)))
    }
    )
    @PutMapping(value = "/{numeroCuenta}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO> update(@Valid @RequestBody CuentaRequest request,
                                         @PathVariable(value = "numeroCuenta", required = true) String numeroCuenta) {
        return new ResponseEntity<>(cuentaService.update(numeroCuenta, request), HttpStatus.OK);
    }

    @Operation(summary = "Buscar Cuentas Bancarias", description = "Operación donde se busca las cuentas bancarias en Base de Datos por su numero de cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda de cuentas bancarias", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ResponseStatusException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseStatusException.class)))
    }
    )
    @GetMapping(value = "/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO> findAll(@PathVariable(name = "pageNo", required = true) int pageNo,
                                                               @PathVariable(name = "pageSize", required = true) int pageSize) {
        return new ResponseEntity<>(cuentaService.listAll(pageNo, pageSize), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar Cuenta Bancaria", description = "Operación donde se elimina una cuenta bancaria en Base de Datos por su numero de cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminación de una Cuenta Bancaria"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ResponseStatusException.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ResponseStatusException.class)))
    }
    )
    @DeleteMapping(value = "/numero-cuenta/{numeroCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable(value = "numeroCuenta", required = true) String numeroCuenta) {
        cuentaService.delete(numeroCuenta);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
