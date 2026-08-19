package com.taller.incidentes.cadena;

import com.taller.incidentes.modelo.Incidente;
import com.taller.incidentes.modelo.TipoIncidente;

/** Manejador especializado en incidentes de tipo ELECTRICO. */
public class ManejadorElectrico extends ManejadorIncidente {

    private static final double COSTO_BASE = 100.0;

    @Override
    protected boolean puedeManejar(Incidente incidente) {
        return incidente.getTipo() == TipoIncidente.ELECTRICO;
    }

    @Override
    protected String procesar(Incidente incidente) {
        System.out.println("Registrando incidente de tipo ELECTRICO...");
        validarDescripcion(incidente);
        System.out.println("Asignando especialista en electricidad");
        double costo = calcularCostoConRecargo(COSTO_BASE, incidente.isUrgente());
        System.out.println("Notificando al cliente sobre incidente electrico");
        return "Incidente ELECTRICO procesado. Costo estimado: " + costo;
    }
}
