package com.taller.incidentes.cadena;

import com.taller.incidentes.modelo.Incidente;
import com.taller.incidentes.modelo.TipoIncidente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias enfocadas en el patron Chain of Responsibility en si
 * mismo: que cada manejador reconozca correctamente su tipo de incidente,
 * que la delegacion al siguiente manejador funcione y que la cadena se
 * pueda ensamblar de forma independiente a GestorIncidentes.
 */
class ManejadorIncidenteChainTest {

    @Test
    void manejadorMotorPuedeManejarIncidentesDeMotor() {
        ManejadorMotor manejador = new ManejadorMotor();
        Incidente incidente = new Incidente("1", TipoIncidente.MOTOR, "Ruido", false);
        assertTrue(manejador.puedeManejar(incidente));
    }

    @Test
    void manejadorMotorNoManejaOtrosTipos() {
        ManejadorMotor manejador = new ManejadorMotor();
        Incidente incidente = new Incidente("2", TipoIncidente.ELECTRICO, "Falla", false);
        assertFalse(manejador.puedeManejar(incidente));
    }

    @Test
    void manejadorElectricoPuedeManejarIncidentesElectricos() {
        ManejadorElectrico manejador = new ManejadorElectrico();
        Incidente incidente = new Incidente("3", TipoIncidente.ELECTRICO, "Corto", false);
        assertTrue(manejador.puedeManejar(incidente));
    }

    @Test
    void manejadorGeneralSiempreManeja() {
        ManejadorGeneral manejador = new ManejadorGeneral();
        Incidente incidente = new Incidente("4", TipoIncidente.DESCONOCIDO, "d", false);
        assertTrue(manejador.puedeManejar(incidente));
    }

    @Test
    void cadenaDelegaAlSiguienteCuandoNoPuedeManejar() {
        ManejadorMotor motor = new ManejadorMotor();
        ManejadorElectrico electrico = new ManejadorElectrico();
        motor.setSiguiente(electrico);

        Incidente incidente = new Incidente("5", TipoIncidente.ELECTRICO, "Corto", false);
        String resultado = motor.manejar(incidente);
        assertEquals("Incidente ELECTRICO procesado. Costo estimado: 100.0", resultado);
    }

    @Test
    void cadenaRetornaMensajePorDefectoSiNadieLaManeja() {
        ManejadorMotor motor = new ManejadorMotor();
        Incidente incidente = new Incidente("6", TipoIncidente.ELECTRICO, "Corto", false);
        String resultado = motor.manejar(incidente);
        assertNotNull(resultado);
        assertEquals("No existe un manejador disponible para el incidente", resultado);
    }

    @Test
    void getSiguienteRetornaElManejadorConfigurado() {
        ManejadorMotor motor = new ManejadorMotor();
        ManejadorElectrico electrico = new ManejadorElectrico();
        motor.setSiguiente(electrico);
        assertSame(electrico, motor.getSiguiente());
    }

    @Test
    void getSiguienteEsNuloPorDefecto() {
        ManejadorMotor motor = new ManejadorMotor();
        assertNull(motor.getSiguiente());
    }

    @Test
    void manejadorCarroceriaCalculaCostoConRecargoDeUrgencia() {
        ManejadorCarroceria manejador = new ManejadorCarroceria();
        Incidente incidente = new Incidente("7", TipoIncidente.CARROCERIA, "Golpe", true);
        String resultado = manejador.procesar(incidente);
        assertEquals("Incidente de CARROCERIA procesado. Costo estimado: 300.0", resultado);
    }

    @Test
    void manejadorNeumaticosValidaDescripcionVacia() {
        ManejadorNeumaticos manejador = new ManejadorNeumaticos();
        Incidente incidente = new Incidente("8", TipoIncidente.NEUMATICOS, "", false);
        assertThrows(IllegalArgumentException.class, () -> manejador.procesar(incidente));
    }

    @Test
    void cadenaCompletaProcesaTipoDesconocidoAlFinal() {
        ManejadorMotor motor = new ManejadorMotor();
        ManejadorElectrico electrico = new ManejadorElectrico();
        ManejadorGeneral general = new ManejadorGeneral();
        motor.setSiguiente(electrico);
        electrico.setSiguiente(general);

        Incidente incidente = new Incidente("9", TipoIncidente.DESCONOCIDO, "x", false);
        String resultado = motor.manejar(incidente);
        assertTrue(resultado.contains("atencion general"));
    }
}
