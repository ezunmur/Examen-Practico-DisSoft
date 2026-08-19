package com.taller.incidentes.cadena;

import com.taller.incidentes.modelo.Incidente;

/**
 * Manejador de respaldo (catch-all) que cierra la cadena de
 * responsabilidad. Atiende cualquier incidente que ningun manejador
 * especializado haya podido procesar (equivalente a la rama "else" del
 * codigo original).
 */
public class ManejadorGeneral extends ManejadorIncidente {

    private static final double COSTO_BASE = 30.0;

    @Override
    protected boolean puedeManejar(Incidente incidente) {
        return true;
    }

    @Override
    protected String procesar(Incidente incidente) {
        System.out.println("Tipo de incidente desconocido, se deriva a atencion general");
        return "Incidente derivado a atencion general. Costo estimado: " + COSTO_BASE;
    }
}
