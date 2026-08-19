package com.taller.incidentes.cadena;

import com.taller.incidentes.modelo.Incidente;
import com.taller.incidentes.modelo.TipoIncidente;

/** Manejador especializado en incidentes de tipo NEUMATICOS. */
public class ManejadorNeumaticos extends ManejadorIncidente {

    private static final double COSTO_BASE = 50.0;

    @Override
    protected boolean puedeManejar(Incidente incidente) {
        return incidente.getTipo() == TipoIncidente.NEUMATICOS;
    }

    @Override
    protected String procesar(Incidente incidente) {
        System.out.println("Registrando incidente de tipo NEUMATICOS...");
        validarDescripcion(incidente);
        System.out.println("Asignando especialista en neumaticos");
        double costo = calcularCostoConRecargo(COSTO_BASE, incidente.isUrgente());
        System.out.println("Notificando al cliente sobre incidente de neumaticos");
        return "Incidente de NEUMATICOS procesado. Costo estimado: " + costo;
    }
}
