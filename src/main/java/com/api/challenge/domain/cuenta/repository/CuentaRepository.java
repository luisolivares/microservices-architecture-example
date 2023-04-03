package com.api.challenge.domain.cuenta.repository;

import com.api.challenge.domain.cuenta.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("cuentaRepository")
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query(value = "SELECT c FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta  ")
    Optional<Cuenta> findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Modifying
    @Query(value = "DELETE FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta ")
    void deleteByIdCliente(@Param("numeroCuenta") String numeroCuenta);

}
