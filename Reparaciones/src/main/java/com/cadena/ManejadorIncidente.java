package com.taller.incidentes.cadena;

import com.taller.incidentes.modelo.Incidente;

/**
 * Manejador base del patron Chain of Responsibility.
 *
 * Cada manejador concreto decide si puede procesar el {@link Incidente}
 * recibido (metodo {@link #puedeManejar(Incidente)}). Si no puede, la
 * responsabilidad se delega automaticamente al siguiente manejador de la
 * cadena mediante el metodo plantilla {@link #manejar(Incidente)}.
 *
 * Esta clase tambien concentra, mediante Extract Method /
 * Consolidate Duplicate Conditional Fragments, la logica que antes estaba
 * duplicada en cada rama del metodo original
 * GestorIncidentes.procesarIncidente: la validacion de la descripcion y el
 * calculo del recargo por urgencia.
 */
public abstract class ManejadorIncidente {

    /** Porcentaje de recargo aplicado a incidentes urgentes (antes "magic number" 1.5). */
    protected static final double RECARGO_URGENCIA = 1.5;

    private ManejadorIncidente siguiente;

    public void setSiguiente(ManejadorIncidente siguiente) {
        this.siguiente = siguiente;
    }

    public ManejadorIncidente getSiguiente() {
        return siguiente;
    }

    /**
     * Metodo plantilla (Template Method): define el flujo comun de la
     * cadena. Las subclases solo deben implementar {@link #puedeManejar}
     * y {@link #procesar}.
     */
    public final String manejar(Incidente incidente) {
        if (puedeManejar(incidente)) {
            return procesar(incidente);
        }
        if (siguiente != null) {
            return siguiente.manejar(incidente);
        }
        return "No existe un manejador disponible para el incidente";
    }

    protected abstract boolean puedeManejar(Incidente incidente);

    protected abstract String procesar(Incidente incidente);

    /**
     * Validacion comun extraida de las cuatro ramas duplicadas del codigo
     * original (Extract Method).
     */
    protected void validarDescripcion(Incidente incidente) {
        if (incidente.getDescripcion() == null || incidente.getDescripcion().isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacia");
        }
    }

    /**
     * Calculo de costo con recargo por urgencia, extraido de las cuatro
     * ramas duplicadas del codigo original (Extract Method).
     */
    protected double calcularCostoConRecargo(double costoBase, boolean urgente) {
        if (urgente) {
            System.out.println("Incidente urgente, se prioriza la atencion");
            return costoBase * RECARGO_URGENCIA;
        }
        return costoBase;
    }
}
