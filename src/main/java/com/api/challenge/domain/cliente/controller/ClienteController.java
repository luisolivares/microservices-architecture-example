package com.api.challenge.domain.cliente.controller;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cliente.model.entity.Cliente;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.cliente.model.request.ClienteRequest;
import com.api.challenge.domain.cliente.service.ClienteService;
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

import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/v1/cliente")
@RestController()
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Crear cliente", description = "Operación donde se crea un cliente en Base de Datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creación de un cliente", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO<Cliente>> create(@Valid @RequestBody ClienteRequest request) {
        return new ResponseEntity<>(clienteService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Modificar cliente", description = "Operación donde se modifica un cliente en Base de Datos por su tipo y número de documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Modificación de un cliente", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @PutMapping(value = "/tipo-documento/{tipoDocumento}/documento/{documento}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@Valid @RequestBody ClienteRequest request, @PathVariable(value = "tipoDocumento", required = true) TipoDocumento tipoDocumento,
                                         @PathVariable(value = "documento", required = true) String documento) {
        return new ResponseEntity<>(clienteService.update(tipoDocumento, documento, request), HttpStatus.OK);
    }

    @Operation(summary = "Busquedas de clientes", description = "Operación donde se busca un listado de clientes en Base de Datos.")
    @ApiResponses(value = {
            //@ApiResponse(responseCode = "200", description = "Búsquedas de clientes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))),
            @ApiResponse(responseCode = "200", description = "Búsquedas de clientes", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @GetMapping(value = "/{pageNo}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO<List<Cliente>>> findAll(@PathVariable(name = "pageNo", required = true) Integer pageNo,
                                                                 @PathVariable(name = "pageSize", required = true) Integer pageSize) {
        return new ResponseEntity<>(clienteService.listAll(pageNo, pageSize), HttpStatus.OK);
    }

    @Operation(summary = "Buscar cliente", description = "Operación donde se busca un cliente en Base de Datos por su tipo y número de documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de Clientes", content = @Content(schema = @Schema(implementation = ResponseApiDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @GetMapping(value = "/tipo-documento/{tipoDocumento}/documento/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseApiDTO<Cliente>> findByDocument(@PathVariable(value = "tipoDocumento", required = true) TipoDocumento tipoDocumento, @PathVariable(value = "documento", required = true) String documento) {
        return new ResponseEntity<>(clienteService.buscarPorTipoDocumento(tipoDocumento, documento), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar cliente", description = "Operación donde se elimina un cliente en Base de Datos por su tipo y número de documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminación de un cliente"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ApiError.class)))
    }
    )
    @DeleteMapping(value = "/tipo-documento/{tipoDocumento}/documento/{documento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable(value = "tipoDocumento", required = true) TipoDocumento tipoDocumento, @PathVariable(value = "numeroCliente", required = true) String documento) {
        clienteService.delete(tipoDocumento, documento);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}