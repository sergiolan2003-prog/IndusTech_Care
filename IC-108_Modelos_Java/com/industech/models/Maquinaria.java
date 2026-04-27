package com.industech.models;

import java.util.Date;

public class Maquinaria {
    private int idMaquinaria;
    private String tipo;
    private Date fecha;
    private String descripcion;
    private String estado;

    public Maquinaria() {}

    public Maquinaria(int idMaquinaria, String tipo, Date fecha, String descripcion, String estado) {
        this.idMaquinaria = idMaquinaria;
        this.tipo = tipo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdMaquinaria() { return idMaquinaria; }
    public void setIdMaquinaria(int idMaquinaria) { this.idMaquinaria = idMaquinaria; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void actualizarEstado(String nuevoEstado) { this.estado = nuevoEstado; }

    @Override
    public String toString() {
        return "Maquinaria{id=" + idMaquinaria + ", tipo='" + tipo + "', estado='" + estado + "'}";
    }
}
