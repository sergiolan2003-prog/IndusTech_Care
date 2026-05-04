package com.industech.models;

import java.util.Date;

public class Falla {
    private int idFalla;
    private Date fecha;
    private String descripcion;
    private String gravedad; // "BAJA", "MEDIA", "ALTA"

    public Falla() {}

    public Falla(int idFalla, Date fecha, String descripcion, String gravedad) {
        this.idFalla = idFalla;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.gravedad = gravedad;
    }

    public int getIdFalla() { return idFalla; }
    public void setIdFalla(int idFalla) { this.idFalla = idFalla; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getGravedad() { return gravedad; }
    public void setGravedad(String gravedad) { this.gravedad = gravedad; }

    public String evaluarGravedad() { return this.gravedad; }

    @Override
    public String toString() {
        return "Falla{id=" + idFalla + ", gravedad='" + gravedad + "', descripcion='" + descripcion + "'}";
    }
}
