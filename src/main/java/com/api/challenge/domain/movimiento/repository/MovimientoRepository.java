package com.api.challenge.domain.movimiento.repository;

import com.api.challenge.domain.cliente.model.enumerated.TipoDocumento;
import com.api.challenge.domain.movimiento.model.dto.IMovimientoDTO;
import com.api.challenge.domain.movimiento.model.entity.Movimiento;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("movimientoRepository")
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query(
            value = "SELECT \n" +
                    "            movimiento.fechaTransaccion as fecha, CONCAT(cliente.nombres, ' ', cliente.apellidos) as cliente, cuenta.tipoCuenta as tipoCuenta, cuenta.divisa as divisa, cuenta.numeroCuenta as numeroCuenta, movimiento.tipoMovimiento as tipoMovimiento, cuenta.saldoInicial as saldoInicial, movimiento.saldoMovimiento as saldoMovimiento, movimiento.saldoDisponible as saldoDisponible\n" +
                    "    \n" +
                    "\n" +
                    "    FROM Movimiento movimiento\n" +
                    "    JOIN movimiento.cuenta cuenta\n" +
                    "    JOIN cuenta.cliente cliente\n" +
                    "    WHERE cliente.documento = :documento AND cuenta.numeroCuenta = :numeroCuenta\n" +
                    "    ORDER BY movimiento.fechaTransaccion ASC"
    )
    Page<IMovimientoDTO> findAllByClientAndDate(@Param("documento") String documento, @Param("numeroCuenta") String numeroCuenta, Pageable pageable);

    @Query(
            value = "SELECT \n" +
                    "            movimiento.fechaTransaccion as fecha, CONCAT(cliente.nombres, ' ', cliente.apellidos) as cliente, cuenta.tipoCuenta as tipoCuenta, cuenta.divisa as divisa, cuenta.numeroCuenta as numeroCuenta, movimiento.tipoMovimiento as tipoMovimiento, movimiento.saldoInicial as saldoInicial, movimiento.saldoMovimiento as saldoMovimiento, movimiento.saldoDisponible as saldoDisponible\n" +
                    "    \n" +
                    "\n" +
                    "    FROM Movimiento movimiento\n" +
                    "    JOIN movimiento.cuenta cuenta\n" +
                    "    JOIN cuenta.cliente cliente\n" +
                    "    WHERE cliente.documento = :documento AND cuenta.numeroCuenta = :numeroCuenta\n" +
                    "    ORDER BY movimiento.fechaTransaccion ASC"
    )
    Page<Tuple> findAllByClient(@Param("documento") String documento, @Param("numeroCuenta") String numeroCuenta, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta")
    void deleteByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Query(value = "SELECT movimiento \n" +
            "    FROM Movimiento movimiento\n" +
            "    JOIN movimiento.cuenta cuenta\n" +
            "    JOIN cuenta.cliente cliente\n" +
            "    WHERE cuenta.numeroCuenta = :numeroCuenta AND cliente.documento = :numeroDocumento AND cliente.tipoDocumento = :tipoDocumento")
    Optional<List<Movimiento>> findByNumeroCuentaAndDocumento(@Param("numeroCuenta") String numeroCuenta, @Param("tipoDocumento") TipoDocumento tipoDocumento, @Param("numeroDocumento") String numeroDocumento);

}