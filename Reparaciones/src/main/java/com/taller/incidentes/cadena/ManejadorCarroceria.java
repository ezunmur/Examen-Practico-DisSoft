package com.taller.incidentes.cadena;

import com.taller.incidentes.modelo.Incidente;
import com.taller.incidentes.modelo.TipoIncidente;

/* Incidentes de tipo CARROCERIA. */

public class ManejadorCarroceria extends ManejadorIncidente {

    private static final double COSTO_BASE = 200.0;

    @Override
    protected boolean puedeManejar(Incidente incidente) {
        return incidente.getTipo() == TipoIncidente.CARROCERIA;
    }

    @Override
    protected String procesar(Incidente incidente) {
        System.out.println("Registrando incidente de tipo CARROCERIA...");
        validarDescripcion(incidente);
        System.out.println("Asignando especialista en carroceria");
        double costo = calcularCostoConRecargo(COSTO_BASE, incidente.isUrgente());
        System.out.println("Notificando al cliente sobre incidente de carroceria");
        return "Incidente de CARROCERIA procesado. Costo estimado: " + costo;
    }
}
