package com.taller.incidentes;

import com.taller.incidentes.cadena.ManejadorCarroceria;
import com.taller.incidentes.cadena.ManejadorElectrico;
import com.taller.incidentes.cadena.ManejadorGeneral;
import com.taller.incidentes.cadena.ManejadorIncidente;
import com.taller.incidentes.cadena.ManejadorMotor;
import com.taller.incidentes.cadena.ManejadorNeumaticos;
import com.taller.incidentes.modelo.Incidente;

/*
Antes de la refactorizacion, esta clase concentraba en un unico metodo
(Long Method) toda la logica de negocio para los cinco tipos de
incidente, con una larga cadena de if/else (Switch Statements) y bloques
de codigo casi identicos repetidos (Duplicated Code).

Ahora la clase solo se encarga de construir la cadena de manejadores
(patron Chain of Responsibility) y delegarle el procesamiento. Agregar
un nuevo tipo de incidente ya no requiere modificar esta clase: basta
con crear un nuevo ManejadorIncidente e insertarlo en la cadena
(principio abierto/cerrado).
*/
public class GestorIncidentes {

    private final ManejadorIncidente cadenaManejadores;

    public GestorIncidentes() {
        ManejadorIncidente motor = new ManejadorMotor();
        ManejadorIncidente electrico = new ManejadorElectrico();
        ManejadorIncidente carroceria = new ManejadorCarroceria();
        ManejadorIncidente neumaticos = new ManejadorNeumaticos();
        ManejadorIncidente general = new ManejadorGeneral();

        motor.setSiguiente(electrico);
        electrico.setSiguiente(carroceria);
        carroceria.setSiguiente(neumaticos);
        neumaticos.setSiguiente(general);

        this.cadenaManejadores = motor;
    }

    public String procesarIncidente(Incidente incidente) {
        return cadenaManejadores.manejar(incidente);
    }
}
