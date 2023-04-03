package com.api.challenge.domain.movimiento.controller;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.movimiento.model.request.MovimientoRequest;
import com.api.challenge.domain.movimiento.service.MovimientoService;
import com.api.challenge.exceptions.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/v1/movimiento")
@RestController()
public class MovimientoController {

    private final MovimientoService movimientoService;

    @Operation(summary = "Registrar un movimiento bancario.", description = "Operación donde se crea un movimiento bancario para un cliente en Base de Datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creación de un movimiento bancario", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO> create(@Valid @RequestBody MovimientoRequest request) {
        return new ResponseEntity<>(movimientoService.saveOrUpdate(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Busqueda de movimientos", description = "Operación donde se busca los movimientos bancarios en Base de Datos por su tipo y número de documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de clientes", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @GetMapping(value = "/{pageNo}/{pageSize}/{tipoDocumento}/{numeroDocumento}/{numeroCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO> findAll(@PathVariable(name = "pageNo", required = true) int pageNo,
                                                       @PathVariable(name = "pageSize", required = true) int pageSize,
                                                       @PathVariable(name = "tipoDocumento", required = true) TipoDocumento tipoDocumento,
                                                       @PathVariable(name = "numeroDocumento", required = true) String numeroDocumento,
                                                       @PathVariable(name = "numeroCuenta", required = true) String numeroCuenta) {
        return new ResponseEntity<>(movimientoService.listAllByDocumentTuple(pageNo, pageSize, tipoDocumento, numeroDocumento, numeroCuenta), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar movimiento", description = "Operación donde se elimina un movimiento bancario en Base de Datos por su tipo y número de documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminación de un movimiento bancario"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @DeleteMapping(value = "/tipo-documento/{tipoDocumento}/documento/{documento}/numero-cuenta/{numeroCuenta}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable(value = "tipoDocumento", required = true) TipoDocumento tipoDocumento,
                                         @PathVariable(value = "documento", required = true) String documento,
                                         @PathVariable(value = "numeroCuenta", required = true) String numeroCuenta) {
        movimientoService.delete(numeroCuenta, tipoDocumento, documento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
