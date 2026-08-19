package com.taller.incidentes;

import com.taller.incidentes.modelo.Incidente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GestorIncidentesTest {

    private final GestorIncidentes gestor = new GestorIncidentes();

    @Test
    void procesaIncidenteDeMotorNoUrgente() {
        Incidente incidente = new Incidente("1", "MOTOR", "Ruido extrano", false);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente de MOTOR procesado. Costo estimado: 150.0", resultado);
    }

    @Test
    void procesaIncidenteDeMotorUrgente() {
        Incidente incidente = new Incidente("2", "MOTOR", "Humo en el motor", true);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente de MOTOR procesado. Costo estimado: 225.0", resultado);
    }

    @Test
    void procesaIncidenteElectricoNoUrgente() {
        Incidente incidente = new Incidente("3", "ELECTRICO", "Corto circuito", false);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente ELECTRICO procesado. Costo estimado: 100.0", resultado);
    }

    @Test
    void procesaIncidenteDeCarroceriaUrgente() {
        Incidente incidente = new Incidente("4", "CARROCERIA", "Abolladura grande", true);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente de CARROCERIA procesado. Costo estimado: 300.0", resultado);
    }

}
