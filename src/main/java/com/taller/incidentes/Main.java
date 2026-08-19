package com.taller.incidentes;

import com.taller.incidentes.modelo.Incidente;
import com.taller.incidentes.modelo.TipoIncidente;

/**
 * Clase de demostracion. Permite ejecutar manualmente el flujo de
 * procesamiento de incidentes (no es requerida para las pruebas).
 */
public class Main {

    public static void main(String[] args) {
        GestorIncidentes gestor = new GestorIncidentes();

        Incidente incidente1 = new Incidente("1", TipoIncidente.MOTOR, "Ruido extrano en el motor", false);
        Incidente incidente2 = new Incidente("2", TipoIncidente.ELECTRICO, "Falla en el sistema electrico", true);
        Incidente incidente3 = new Incidente("3", TipoIncidente.DESCONOCIDO, "Incidente sin categoria clara", false);

        System.out.println(gestor.procesarIncidente(incidente1));
        System.out.println(gestor.procesarIncidente(incidente2));
        System.out.println(gestor.procesarIncidente(incidente3));
    }
}
