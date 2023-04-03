package com.api.challenge.domain.cuenta.service.impl;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cuenta.model.entity.Cuenta;
import com.api.challenge.domain.cuenta.model.request.CuentaRequest;
import com.api.challenge.domain.cuenta.repository.CuentaRepository;
import com.api.challenge.domain.cuenta.service.CuentaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("cuentaServiceImpl")
public class CuentaServiceImpl implements CuentaService {

    @Qualifier("cuentaRepository")
    private final CuentaRepository cuentaRepository;

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public ResponseApiDTO create(CuentaRequest request) {
        try {
            Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta());
            if (cuenta.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Cuenta %s ya existente", request.getNumeroCuenta()));
            } else {
                ResponseApiDTO<Cuenta> response = new ResponseApiDTO<>();
                Cuenta c = new Cuenta();
                c.setNumeroCuenta(request.getNumeroCuenta());
                c.setDivisa(request.getDivisa());
                c.setSaldoInicial(request.getSaldoInicial());
                c.setTipoCuenta(request.getTipoCuenta());
                c.setEstado(request.getEstado());
                response.setData(cuentaRepository.save(c));
                return response;
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear una cuenta bancaria", ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        ResponseApiDTO<Cuenta> response = new ResponseApiDTO<>();
        Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta.isPresent()) {
            response.setData(cuenta.get());
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cuenta %s no encontrada", numeroCuenta));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO<List<Cuenta>> listAll(int pageNo, int pageSize) {
        ResponseApiDTO<List<Cuenta>> response = new ResponseApiDTO<>();
        String sortBy = "numeroCuenta";
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Cuenta> pagedResult = this.cuentaRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            response.setData(pagedResult.getContent());
            return response;
        } else {
            return response;
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public ResponseApiDTO<Cuenta> update(String numeroCuenta, CuentaRequest request) {
        ResponseApiDTO<Cuenta> response = new ResponseApiDTO<>();
        Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta.isPresent()) {
            cuenta.get().setNumeroCuenta(request.getNumeroCuenta());
            cuenta.get().setDivisa(request.getDivisa());
            cuenta.get().setSaldoInicial(request.getSaldoInicial());
            cuenta.get().setTipoCuenta(request.getTipoCuenta());
            cuenta.get().setEstado(request.getEstado());
            response.setData(cuentaRepository.saveAndFlush(cuenta.get()));
            return response;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cuenta %s no encontrada", numeroCuenta));
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public void delete(String numeroCuenta) {
        Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta.isPresent()) {
            cuentaRepository.delete(cuenta.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cuenta %s no encontrada", numeroCuenta));
        }
    }
}