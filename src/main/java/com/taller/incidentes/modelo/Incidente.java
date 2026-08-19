package com.taller.incidentes.modelo;

public class Incidente {

    private final String id;
    private final TipoIncidente tipo;
    private final String descripcion;
    private final boolean urgente;

    public Incidente(String id, TipoIncidente tipo, String descripcion, boolean urgente) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.urgente = urgente;
    }

    public String getId() {
        return id;
    }

    public TipoIncidente getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isUrgente() {
        return urgente;
    }
}
