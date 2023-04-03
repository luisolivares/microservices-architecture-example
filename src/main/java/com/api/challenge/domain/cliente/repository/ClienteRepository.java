package com.api.challenge.domain.cliente.repository;

import com.api.challenge.domain.cliente.model.entity.Cliente;
import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT c FROM Cliente c WHERE c.tipoDocumento = :tipoDocumento AND c.documento = :documento ")
    Optional<Cliente> findByTipoNumeroDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento, @Param("documento") String documento);

    @Modifying
    @Query(value = "DELETE FROM Cliente c WHERE c.tipoDocumento = :tipoDocumento AND c.documento = :documento ")
    void deleteByTipoNumeroDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento, @Param("documento") String documento);

}
