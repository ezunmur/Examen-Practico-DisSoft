package com.taller.incidentes.modelo;
/*
Tipo de incidente reportado en el taller.
Antes de la refactorizacion, el tipo de incidente se representaba con
 un String suelto ("MOTOR", "ELECTRICO", ...) */
 
public enum TipoIncidente {
    MOTOR,
    ELECTRICO,
    CARROCERIA,
    NEUMATICOS,
    DESCONOCIDO
}
