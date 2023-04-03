package com.api.challenge.domain.cliente.service.impl;

import com.api.challenge.common.ResponseApiDTO;
import com.api.challenge.domain.cliente.model.entity.Cliente;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.cliente.model.request.ClienteRequest;
import com.api.challenge.domain.cliente.repository.ClienteRepository;
import com.api.challenge.domain.cliente.service.ClienteService;
import com.api.challenge.utils.ChallengeUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("clienteServiceImpl")
public class ClienteServiceImpl implements ClienteService {

    @Qualifier("clienteRepository")
    private final ClienteRepository clienteRepository;

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public ResponseApiDTO create(ClienteRequest request) {
        try {
            Optional<Cliente> result = clienteRepository.findByTipoNumeroDocumento(request.getTipoDocumento(), request.getDocumento());
            if (!result.isPresent()) {
                ResponseApiDTO<Cliente> responseApiDTO = new ResponseApiDTO<>();
                Cliente cliente = new Cliente();
                cliente.setApellidos(request.getApellidos());
                cliente.setContraseña(request.getContraseña());
                cliente.setDocumento(request.getDocumento());
                cliente.setEmail(request.getEmail());
                cliente.setEstado(request.getEstado());
                cliente.setNombres(request.getNombres());
                cliente.setGenero(request.getGenero());
                cliente.setFechaNacimiento(ChallengeUtils.parseFecha(request.getFechaNacimiento()));
                cliente.setTipoDocumento(request.getTipoDocumento());
                cliente.setTelefono(request.getTelefono());
                responseApiDTO.setData(clienteRepository.save(cliente));
                return responseApiDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cliente %s %s existe en la Base de Datos.", result.get().getTipoDocumento(), result.get().getDocumento()));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear un cliente", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO listAll(int pageNo, int pageSize) {
        String sortBy = "documento";
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Cliente> pagedResult = this.clienteRepository.findAll(paging);
        ResponseApiDTO<List<Cliente>> responseApiDTO = new ResponseApiDTO<>();
        if (pagedResult.hasContent()) {
            responseApiDTO.setData(pagedResult.getContent());
            return responseApiDTO;
        } else {
            responseApiDTO.setData(new ArrayList<>());
            return responseApiDTO;
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public ResponseApiDTO update(TipoDocumento tipoDocumento, String documento, ClienteRequest request) {
        try {
            ResponseApiDTO<Cliente> responseApiDTO = new ResponseApiDTO<>();
            Optional<Cliente> cliente = clienteRepository.findByTipoNumeroDocumento(tipoDocumento, documento);
            if (cliente.isPresent()) {
                cliente.get().setApellidos(request.getApellidos());
                cliente.get().setContraseña(request.getContraseña());
                cliente.get().setDocumento(request.getDocumento());
                cliente.get().setEmail(request.getEmail());
                cliente.get().setNombres(request.getNombres());
                cliente.get().setGenero(request.getGenero());
                cliente.get().setFechaNacimiento(ChallengeUtils.parseFecha(request.getFechaNacimiento()));
                cliente.get().setTipoDocumento(request.getTipoDocumento());
                cliente.get().setTelefono(request.getTelefono());
                responseApiDTO.setData(clienteRepository.saveAndFlush(cliente.get()));
                return responseApiDTO;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cliente %s %s no encontrado", request.getTipoDocumento(), request.getDocumento()));
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al modificar el cliente", e);
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public void delete(TipoDocumento tipoDocumento, String documento) {
        Optional<Cliente> cliente = clienteRepository.findByTipoNumeroDocumento(tipoDocumento, documento);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cliente %s %s no encontrado.", tipoDocumento, documento));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApiDTO buscarPorTipoDocumento(TipoDocumento tipoDocumento, String documento) {
        Optional<Cliente> cliente = clienteRepository.findByTipoNumeroDocumento(tipoDocumento, documento);
        ResponseApiDTO<Cliente> responseApiDTO = new ResponseApiDTO<>();
        if (cliente.isPresent()) {
            responseApiDTO.setData(cliente.get());
            return responseApiDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
    }
}