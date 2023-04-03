package com.api.challenge.domain.movimiento.service.impl;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cliente.model.entity.Cliente;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.cliente.repository.ClienteRepository;
import com.api.challenge.domain.cuenta.model.entity.Cuenta;
import com.api.challenge.domain.cuenta.model.enumerated.Divisa;
import com.api.challenge.domain.cuenta.model.enumerated.TipoCuenta;
import com.api.challenge.domain.cuenta.repository.CuentaRepository;
import com.api.challenge.domain.movimiento.model.dto.IMovimientoDTO;
import com.api.challenge.domain.movimiento.model.dto.MovimientoDTO;
import com.api.challenge.domain.movimiento.model.entity.Movimiento;
import com.api.challenge.domain.movimiento.model.enumerated.TipoMovimiento;
import com.api.challenge.domain.movimiento.model.request.MovimientoRequest;
import com.api.challenge.domain.movimiento.repository.MovimientoRepository;
import com.api.challenge.domain.movimiento.service.MovimientoService;
import com.api.challenge.utils.ChallengeUtils;
import jakarta.persistence.Tuple;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("movimientoServiceImpl")
public class MovimientoServiceImpl implements MovimientoService {

    @Qualifier("movimientoRepository")
    private final MovimientoRepository movimientoRepository;

    @Qualifier("clienteRepository")
    private final ClienteRepository clienteRepository;

    @Qualifier("cuentaRepository")
    private final CuentaRepository cuentaRepository;

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO listAll(int pageNo, int pageSize) {
        String sortBy = "fechaTransaccion";
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Movimiento> pagedResult = movimientoRepository.findAll(paging);
        ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        if (pagedResult.hasContent()) {
            responseApiDTO.setData(pagedResult.getContent());
            return responseApiDTO;
        } else {
            responseApiDTO.setData(new ArrayList<>());
            return responseApiDTO;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO listAllByDocumentTuple(int pageNo, int pageSize, TipoDocumento tipoDocumento, String documento, String numeroCuenta) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Tuple> pagedResult = movimientoRepository.findAllByClient(documento, numeroCuenta, paging);
        ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        if (pagedResult.hasContent()) {
            responseApiDTO.setData(
                    pagedResult.getContent().stream()
                            .map(t -> new MovimientoDTO(
                                    t.get("fecha", Date.class),
                                    t.get("cliente", String.class),
                                    t.get("tipoCuenta", TipoCuenta.class),
                                    t.get("divisa", Divisa.class),
                                    t.get("numeroCuenta", String.class),
                                    t.get("tipoMovimiento", TipoMovimiento.class),
                                    t.get("saldoInicial", BigDecimal.class),
                                    t.get("saldoMovimiento", BigDecimal.class),
                                    t.get("saldoDisponible", BigDecimal.class)
                            ))
                            .toList()

            );
            return responseApiDTO;
        } else {
            responseApiDTO.setData(new ArrayList<>());
            return responseApiDTO;
        }
    }

    @Override
    public List<IMovimientoDTO> listAllByDocumentInterface(int pageNo, int pageSize, TipoDocumento tipoDocumento, String documento, String numeroCuenta) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<IMovimientoDTO> pagedResult = movimientoRepository.findAllByClientAndDate(documento, numeroCuenta, paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO getByNumeroCuentaAndDocumento(String numeroCuenta, TipoDocumento tipoDocumento, String documento) {
        Optional<List<Movimiento>> movimiento = movimientoRepository.findByNumeroCuentaAndDocumento(numeroCuenta, tipoDocumento, documento);
        ResponseApiDTO responseApiDTO = new ResponseApiDTO();
        if (movimiento.isPresent()) {
            responseApiDTO.setData(movimiento.get());
            return responseApiDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado.");
        }
    }

    @Transactional
    @Override
    public ResponseApiDTO saveOrUpdate(MovimientoRequest request) {
        try {
            ResponseApiDTO<Movimiento> responseApiDTO = new ResponseApiDTO();
            Optional<Cliente> cliente = clienteRepository.findByTipoNumeroDocumento(request.getTipoDocumento(), request.getDocumento());
            Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta());
            if (cliente.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cliente %s %s no encontrado", request.getTipoDocumento(), request.getDocumento()));
            }
            if (cuenta.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cuenta %s no encontrada", request.getNumeroCuenta()));
            }
            Movimiento movimiento = new Movimiento();
            BigDecimal saldoInicialCuenta = cuenta.get().getSaldoInicial();
            movimiento.setTipoMovimiento(request.getTipoMovimiento());
            movimiento.setSaldoMovimiento(request.getSaldoMovimiento());
            movimiento.setFechaTransaccion(request.getFechaTransaccion());
            movimiento.setCuenta(cuenta.get());
            movimiento.setSaldoInicial(saldoInicialCuenta);
            movimiento.setSaldoDisponible(ChallengeUtils.transaccionMovimiento(cuenta.get().getSaldoInicial(), request.getSaldoMovimiento(), request.getTipoMovimiento()));
            cuenta.get().setCliente(cliente.get());
            cuenta.get().setSaldoInicial(movimiento.getSaldoDisponible());
            responseApiDTO.setData(movimientoRepository.saveAndFlush(movimiento));
            return responseApiDTO;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en registrar el movimiento");
        }
    }

    @Transactional
    @Override
    public void delete(String numeroCuenta, TipoDocumento tipoDocumento, String documento) {
        Optional<List<Movimiento>> movimiento = movimientoRepository.findByNumeroCuentaAndDocumento(numeroCuenta, tipoDocumento, documento);
        if (movimiento.isPresent()) {
            movimientoRepository.deleteAllInBatch(movimiento.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado.");
        }
    }
}