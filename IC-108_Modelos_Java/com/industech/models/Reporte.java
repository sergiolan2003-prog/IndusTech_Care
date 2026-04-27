package com.industech.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reporte {
    private int idReporte;
    private Date fecha;
    private String tipo;
    private String descripcion;
    private String estado;
    private List<Falla> fallas;

    public Reporte() { this.fallas = new ArrayList<>(); }

    public Reporte(int idReporte, Date fecha, String tipo, String descripcion, String estado) {
        this.idReporte = idReporte;
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fallas = new ArrayList<>();
    }

    public int getIdReporte() { return idReporte; }
    public void setIdReporte(int idReporte) { this.idReporte = idReporte; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Falla> getFallas() { return fallas; }

    public void agregarFalla(Falla falla) { this.fallas.add(falla); }
    public void cerrarReporte() { this.estado = "CERRADO"; }

    @Override
    public String toString() {
        return "Reporte{id=" + idReporte + ", tipo='" + tipo + "', estado='" + estado + "'}";
    }
}
