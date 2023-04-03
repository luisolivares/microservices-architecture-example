package com.api.challenge.domain.cliente.service;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cliente.model.entity.Cliente;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.cliente.model.request.ClienteRequest;

import java.util.List;

public interface ClienteService {

    /**
     * Método encargado de crear un cliente bancario.
     *
     * @param request Request con los datos para crear un cliente bancario.
     * @return
     */
    ResponseApiDTO<Cliente> create(ClienteRequest request);

    /**
     * Método encargado de listar clientes bancarios dado el n° de pagina y n° de registros a vizualizar.
     *
     * @param pageNo   N° de página
     * @param pageSize N° de registros a visualizar.
     * @return
     */
    ResponseApiDTO<List<Cliente>> listAll(int pageNo, int pageSize);

    /**
     * Método encargado de modificar un cliente bancario dado su tipo y n° de documento.
     *
     * @param tipoDocumento Tipo de documento bancario (PASAPORTE, CEDULA)
     * @param documento     N° de documento del cliente bancario.
     * @param request       Request con los datos para crear un cliente bancario.
     * @return
     */
    ResponseApiDTO<Cliente> update(TipoDocumento tipoDocumento, String documento, ClienteRequest request);

    /**
     * Método encargado de eliminar un cliente bancario dado su tipo y n° de documento.
     *
     * @param tipoDocumento Tipo de documento bancario (PASAPORTE, CEDULA)
     * @param documento     N° de documento del cliente bancario.
     */
    void delete(TipoDocumento tipoDocumento, String documento);

    /**
     * Método encargado de buscar un cliente bancario dado su tipo y n° de documento.
     *
     * @param tipoDocumento Tipo de documento bancario (PASAPORTE, CEDULA)
     * @param documento     N° de documento del cliente bancario.
     * @return
     */
    ResponseApiDTO<Cliente> buscarPorTipoDocumento(TipoDocumento tipoDocumento, String documento);

}
