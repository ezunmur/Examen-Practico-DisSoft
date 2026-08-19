package com.taller.incidentes.cadena;

import com.taller.incidentes.modelo.Incidente;
import com.taller.incidentes.modelo.TipoIncidente;

/** Manejador especializado en incidentes de tipo MOTOR. */
public class ManejadorMotor extends ManejadorIncidente {

    private static final double COSTO_BASE = 150.0;

    @Override
    protected boolean puedeManejar(Incidente incidente) {
        return incidente.getTipo() == TipoIncidente.MOTOR;
    }

    @Override
    protected String procesar(Incidente incidente) {
        System.out.println("Registrando incidente de tipo MOTOR...");
        validarDescripcion(incidente);
        System.out.println("Asignando especialista en motores");
        double costo = calcularCostoConRecargo(COSTO_BASE, incidente.isUrgente());
        System.out.println("Notificando al cliente sobre incidente de motor");
        return "Incidente de MOTOR procesado. Costo estimado: " + costo;
    }
}
