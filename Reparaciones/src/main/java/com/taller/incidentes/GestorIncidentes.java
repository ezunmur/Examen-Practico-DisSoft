package com.taller.incidentes;
import com.taller.incidentes.modelo.Incidente;

public class GestorIncidentes {

    public String procesarIncidente(Incidente incidente) {
        String resultado = "";
        double costo = 0;

        if (incidente.getTipo().equals("MOTOR")) {
            System.out.println("Registrando incidente de tipo MOTOR...");
            if (incidente.getDescripcion() == null || incidente.getDescripcion().isEmpty()) {
                throw new IllegalArgumentException("La descripcion no puede estar vacia");
            }
            System.out.println("Asignando especialista en motores");
            costo = 150.0;
            if (incidente.isUrgente()) {
                costo = costo * 1.5;
                System.out.println("Incidente urgente, se prioriza la atencion");
            }
            System.out.println("Notificando al cliente sobre incidente de motor");
            resultado = "Incidente de MOTOR procesado. Costo estimado: " + costo;

        } else if (incidente.getTipo().equals("ELECTRICO")) {
            System.out.println("Registrando incidente de tipo ELECTRICO...");
            if (incidente.getDescripcion() == null || incidente.getDescripcion().isEmpty()) {
                throw new IllegalArgumentException("La descripcion no puede estar vacia");
            }
            System.out.println("Asignando especialista en electricidad");
            costo = 100.0;
            if (incidente.isUrgente()) {
                costo = costo * 1.5;
                System.out.println("Incidente urgente, se prioriza la atencion");
            }
            System.out.println("Notificando al cliente sobre incidente electrico");
            resultado = "Incidente ELECTRICO procesado. Costo estimado: " + costo;

        } else if (incidente.getTipo().equals("CARROCERIA")) {
            System.out.println("Registrando incidente de tipo CARROCERIA...");
            if (incidente.getDescripcion() == null || incidente.getDescripcion().isEmpty()) {
                throw new IllegalArgumentException("La descripcion no puede estar vacia");
            }
            System.out.println("Asignando especialista en carroceria");
            costo = 200.0;
            if (incidente.isUrgente()) {
                costo = costo * 1.5;
                System.out.println("Incidente urgente, se prioriza la atencion");
            }
            System.out.println("Notificando al cliente sobre incidente de carroceria");
            resultado = "Incidente de CARROCERIA procesado. Costo estimado: " + costo;

        } else if (incidente.getTipo().equals("NEUMATICOS")) {
            System.out.println("Registrando incidente de tipo NEUMATICOS...");
            if (incidente.getDescripcion() == null || incidente.getDescripcion().isEmpty()) {
                throw new IllegalArgumentException("La descripcion no puede estar vacia");
            }
            System.out.println("Asignando especialista en neumaticos");
            costo = 50.0;
            if (incidente.isUrgente()) {
                costo = costo * 1.5;
                System.out.println("Incidente urgente, se prioriza la atencion");
            }
            System.out.println("Notificando al cliente sobre incidente de neumaticos");
            resultado = "Incidente de NEUMATICOS procesado. Costo estimado: " + costo;

        } else {
            System.out.println("Tipo de incidente desconocido, se deriva a atencion general");
            costo = 30.0;
            resultado = "Incidente derivado a atencion general. Costo estimado: " + costo;
        }

        return resultado;
    }
}
