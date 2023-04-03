package com.api.challenge.domain.cuenta.service;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cuenta.model.entity.Cuenta;
import com.api.challenge.domain.cuenta.model.request.CuentaRequest;

import java.util.List;

public interface CuentaService {

    /**
     * Método encargado de crear una cuenta bancaria.
     *
     * @param request Request con los datos a crear de la cuenta bancaria.
     * @return
     */
    ResponseApiDTO<Cuenta> create(CuentaRequest request);

    /**
     * Método encargado de buscar una cuenta bancaria dado su número de cuenta.
     *
     * @param numeroCuenta N° de la cuenta bancaria.
     * @return
     */
    ResponseApiDTO<Cuenta> findByNumeroCuenta(String numeroCuenta);

    /**
     * Método encargado de listar las cuentas bancarias dado el n° de página y cantidad de registros a visualizar.
     *
     * @param pageNo   N° de página
     * @param pageSize N° de registros a mostrar.
     * @return
     */
    ResponseApiDTO<List<Cuenta>> listAll(int pageNo, int pageSize);

    /**
     * Método encargado de modificar una cuenta bancaria dado su N° de cuenta.
     *
     * @param numeroCuenta N° de la cuenta bancaria.
     * @param request      Request con los datos a modificar de la cuenta bancaria.
     * @return
     */
    ResponseApiDTO<Cuenta> update(String numeroCuenta, CuentaRequest request);

    /**
     * Método encargado para eliminar una cuenta bancaria dado su n° de cuenta.
     *
     * @param numeroCuenta N° de la cuenta bancaria.
     */
    void delete(String numeroCuenta);

}
