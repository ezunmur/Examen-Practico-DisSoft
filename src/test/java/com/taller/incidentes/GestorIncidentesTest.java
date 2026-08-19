package com.taller.incidentes;

import com.taller.incidentes.modelo.Incidente;
import com.taller.incidentes.modelo.TipoIncidente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas de integracion del GestorIncidentes: validan que la cadena de
 * responsabilidad completa (Motor -> Electrico -> Carroceria -> Neumaticos
 * -> General) produzca el mismo comportamiento observable que el codigo
 * original, para cada tipo de incidente y para los casos de urgencia,
 * tipo desconocido y descripcion invalida.
 */
class GestorIncidentesTest {

    private final GestorIncidentes gestor = new GestorIncidentes();

    @Test
    void procesaIncidenteDeMotorNoUrgente() {
        Incidente incidente = new Incidente("1", TipoIncidente.MOTOR, "Ruido extrano", false);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente de MOTOR procesado. Costo estimado: 150.0", resultado);
    }

    @Test
    void procesaIncidenteDeMotorUrgente() {
        Incidente incidente = new Incidente("2", TipoIncidente.MOTOR, "Humo en el motor", true);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente de MOTOR procesado. Costo estimado: 225.0", resultado);
    }

    @Test
    void procesaIncidenteElectricoNoUrgente() {
        Incidente incidente = new Incidente("3", TipoIncidente.ELECTRICO, "Corto circuito", false);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente ELECTRICO procesado. Costo estimado: 100.0", resultado);
    }

    @Test
    void procesaIncidenteDeCarroceriaUrgente() {
        Incidente incidente = new Incidente("4", TipoIncidente.CARROCERIA, "Abolladura grande", true);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente de CARROCERIA procesado. Costo estimado: 300.0", resultado);
    }

    @Test
    void procesaIncidenteDeNeumaticosNoUrgente() {
        Incidente incidente = new Incidente("5", TipoIncidente.NEUMATICOS, "Llanta pinchada", false);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente de NEUMATICOS procesado. Costo estimado: 50.0", resultado);
    }

    @Test
    void procesaIncidenteDesconocidoSeDerivaAtencionGeneral() {
        Incidente incidente = new Incidente("6", TipoIncidente.DESCONOCIDO, "Caso raro", false);
        String resultado = gestor.procesarIncidente(incidente);
        assertEquals("Incidente derivado a atencion general. Costo estimado: 30.0", resultado);
    }

    @Test
    void lanzaExcepcionSiDescripcionEsVaciaEnIncidenteConocido() {
        Incidente incidente = new Incidente("7", TipoIncidente.MOTOR, "", false);
        assertThrows(IllegalArgumentException.class, () -> gestor.procesarIncidente(incidente));
    }

    @Test
    void lanzaExcepcionSiDescripcionEsNulaEnIncidenteConocido() {
        Incidente incidente = new Incidente("8", TipoIncidente.ELECTRICO, null, false);
        assertThrows(IllegalArgumentException.class, () -> gestor.procesarIncidente(incidente));
    }

    @Test
    void noLanzaExcepcionParaTipoDesconocidoConDescripcionVacia() {
        Incidente incidente = new Incidente("9", TipoIncidente.DESCONOCIDO, "", false);
        assertDoesNotThrow(() -> gestor.procesarIncidente(incidente));
    }

    @Test
    void resultadoNuncaEsNuloNiVacio() {
        Incidente incidente = new Incidente("10", TipoIncidente.NEUMATICOS, "Desgaste", true);
        String resultado = gestor.procesarIncidente(incidente);
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void todosLosTiposConocidosSonProcesadosCorrectamente() {
        assertAll("procesamiento de todos los tipos",
            () -> assertTrue(gestor.procesarIncidente(new Incidente("11", TipoIncidente.MOTOR, "d", false)).contains("MOTOR")),
            () -> assertTrue(gestor.procesarIncidente(new Incidente("12", TipoIncidente.ELECTRICO, "d", false)).contains("ELECTRICO")),
            () -> assertTrue(gestor.procesarIncidente(new Incidente("13", TipoIncidente.CARROCERIA, "d", false)).contains("CARROCERIA")),
            () -> assertTrue(gestor.procesarIncidente(new Incidente("14", TipoIncidente.NEUMATICOS, "d", false)).contains("NEUMATICOS"))
        );
    }
}
