package com.api.challenge.domain.movimiento.service;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.movimiento.model.dto.IMovimientoDTO;
import com.api.challenge.domain.movimiento.model.request.MovimientoRequest;

import java.util.List;

public interface MovimientoService {

    /**
     * Método encargado de listar todos los movimientos bancarios.
     *
     * @param pageNo   N° de página
     * @param pageSize Cantidad de registros
     * @return
     */
    ResponseApiDTO listAll(int pageNo, int pageSize);


    /**
     * Método encargado de obtener el listado de movimientos bancarios dado el tipo y número de documento, número de cuenta así como su página inicial y cantidad de registros.
     *
     * @param pageNo        N° de página
     * @param pageSize      Cantidad de registros
     * @param tipoDocumento Tipo de documento (PASAPORTE, CEDULA) asociado al movimiento.
     * @param documento     N° de documento asociado al movimiento.
     * @param numeroCuenta  N° de la cuenta asociado al movimiento.
     * @return
     */
    ResponseApiDTO listAllByDocumentTuple(int pageNo, int pageSize, TipoDocumento tipoDocumento, String documento, String numeroCuenta);

    /**
     * Método encargado de obtener el listado de movimientos bancarios dado el tipo y número de documento, número de cuenta así como su página inicial y cantidad de registros.
     *
     * @param pageNo        N° de página
     * @param pageSize      Cantidad de registros
     * @param tipoDocumento Tipo de documento (PASAPORTE, CEDULA) asociado al movimiento.
     * @param documento     N° de documento asociado al movimiento.
     * @param numeroCuenta  N° de la cuenta asociado al movimiento.
     * @return
     */
    List<IMovimientoDTO> listAllByDocumentInterface(int pageNo, int pageSize, TipoDocumento tipoDocumento, String documento, String numeroCuenta);


    /**
     * Método encargado de obtener el listado de movimientos bancarios dado el tipo y número de documento, número de cuenta.
     *
     * @param numeroCuenta  N° de la cuenta asociado al movimiento.
     * @param tipoDocumento Tipo de documento (PASAPORTE, CEDULA) asociado al movimiento.
     * @param documento     N° de documento asociado al movimiento.
     * @return
     */
    ResponseApiDTO getByNumeroCuentaAndDocumento(String numeroCuenta, TipoDocumento tipoDocumento, String documento);


    /**
     * Método encargado de crear o modificar un movimiento bancario.
     *
     * @param request Objecto de tipo request que contiene los parametros para registrar o modificar un movimiento bancario.
     * @return
     */
    ResponseApiDTO saveOrUpdate(MovimientoRequest request);

    /**
     * Método encargado de eliminar un movimiento bancario dado el número de cuenta, tipo y número de documento asociado a la misma.
     *
     * @param numeroCuenta  N° de la cuenta asociado al movimiento.
     * @param tipoDocumento Tipo de documento (PASAPORTE, CEDULA) asociado al movimiento.
     * @param documento     N° de documento asociado al movimiento.
     */
    void delete(String numeroCuenta, TipoDocumento tipoDocumento, String documento);
}
