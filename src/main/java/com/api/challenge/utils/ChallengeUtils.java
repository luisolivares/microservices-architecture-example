package com.api.challenge.utils;

import com.api.challenge.common.MetaData;
import com.api.challenge.domain.movimiento.model.enumerated.TipoMovimiento;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChallengeUtils {

    public static final String PATTER_TELEPHONE ="[\\s]*[0-9]*[0-9]+";

    public static Date parseFecha(String fecha) throws Exception {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {

        }
        return fechaDate;
    }

    public static MetaData createPomMetaData(HttpServletRequest httpServletRequest){
        MetaData metaData = new MetaData();
        metaData.setMethod(httpServletRequest.getMethod());
        metaData.setOperation(httpServletRequest.getRequestURI());
        return metaData;
    }

    public static BigDecimal transaccionMovimiento(BigDecimal saldoInicial, BigDecimal saldoMovimiento, TipoMovimiento tipoMovimiento){
        BigDecimal operacion;
        switch (tipoMovimiento.name()) {
            case "RETIRO":
                operacion = saldoInicial.subtract(saldoMovimiento).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                break;
            case "DEPOSITO":
                operacion = saldoInicial.add(saldoMovimiento).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                break;
            case "TRANSFERENCIA":
                operacion = saldoInicial.add(saldoMovimiento).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                break;
            default:
                operacion = BigDecimal.ZERO;
                break;
        }
        return operacion;
    }

}
