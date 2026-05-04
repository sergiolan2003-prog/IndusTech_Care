package com.industech.models;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mantenimiento {

    private int idMantenimiento;
    private String tipo;
    private Date fecha;
    private String descripcion;
    private String estado;

    private LocalTime horaInicio;
    private LocalTime horaFin;

    private double costoManoObra;
    private double costoRepuestos;
    private int    idMaquinaria;

    private List<Tecnico>       tecnicosAsignados = new ArrayList<>();
    private List<RepuestoOrden> repuestosUsados   = new ArrayList<>();

    public Mantenimiento() {}

    public Mantenimiento(int idMantenimiento, String tipo, Date fecha,
                         String descripcion, String estado) {
        this.idMantenimiento = idMantenimiento;
        this.tipo            = tipo;
        this.fecha           = fecha;
        this.descripcion     = descripcion;
        this.estado          = estado;
    }

    public Mantenimiento(int idMantenimiento, String tipo, Date fecha,
                         String descripcion, String estado,
                         LocalTime horaInicio, LocalTime horaFin) {
        this(idMantenimiento, tipo, fecha, descripcion, estado);
        this.horaInicio = horaInicio;
        this.horaFin    = horaFin;
    }

    public Mantenimiento(int idMantenimiento, String tipo, Date fecha,
                         String descripcion, String estado,
                         double costoManoObra, double costoRepuestos, int idMaquinaria) {
        this(idMantenimiento, tipo, fecha, descripcion, estado);
        this.costoManoObra  = costoManoObra;
        this.costoRepuestos = costoRepuestos;
        this.idMaquinaria   = idMaquinaria;
    }

    public int getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(int v) { this.idMantenimiento = v; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public void iniciarMantenimiento()   { this.estado = "ACTIVO"; }
    public void finalizarMantenimiento() { this.estado = "FINALIZADO"; }
    public void cambiarEstado(String nuevoEstado) { this.estado = nuevoEstado; }

    public long calcularDuracion() {
        if (horaInicio == null || horaFin == null) return -1;
        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    public boolean esMantenimientoLargo() { return calcularDuracion() > 120; }

    public void mostrar() {
        System.out.println("ID:          " + idMantenimiento);
        System.out.println("Tipo:        " + tipo);
        System.out.println("Fecha:       " + fecha);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Estado:      " + estado);
        if (horaInicio != null && horaFin != null) {
            System.out.println("Hora inicio: " + horaInicio);
            System.out.println("Hora fin:    " + horaFin);
            System.out.println("Duración:    " + calcularDuracion() + " minutos");
            System.out.println(esMantenimientoLargo() ? "⚠ Mantenimiento largo" : "✔ Mantenimiento normal");
        }
        System.out.println();
    }

    public List<Tecnico> getTecnicosAsignados() { return tecnicosAsignados; }

    public void asignarTecnico(Tecnico t) {
        for (Tecnico existente : tecnicosAsignados) {
            if (existente.getIdTecnico() == t.getIdTecnico()) return;
        }
        tecnicosAsignados.add(t);
    }

    public void desasignarTecnico(int idTecnico) {
        tecnicosAsignados.removeIf(t -> t.getIdTecnico() == idTecnico);
    }

    public String getNombresTecnicos() {
        if (tecnicosAsignados.isEmpty()) return "Sin asignar";
        StringBuilder sb = new StringBuilder();
        for (Tecnico t : tecnicosAsignados) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(t.getNombre());
        }
        return sb.toString();
    }

    public double getCostoManoObra()  { return costoManoObra; }
    public void setCostoManoObra(double v) { this.costoManoObra = v; }

    public double getCostoRepuestos() { return costoRepuestos; }
    public void setCostoRepuestos(double v) { this.costoRepuestos = v; }

    public int getIdMaquinaria()      { return idMaquinaria; }
    public void setIdMaquinaria(int v) { this.idMaquinaria = v; }

    public double getCostoTotal() { return costoManoObra + costoRepuestos; }

    public double calcularCostoManoObraConTarifa(double tarifaHora) {
        long minutos = calcularDuracion();
        if (minutos > 0) return (minutos / 60.0) * tarifaHora;
        return costoManoObra;
    }

    public List<RepuestoOrden> getRepuestosUsados() { return repuestosUsados; }

    public void agregarRepuesto(RepuestoOrden ro) {
        repuestosUsados.add(ro);
        this.costoRepuestos += ro.getSubtotal();
    }

    public void quitarRepuesto(RepuestoOrden ro) {
        if (repuestosUsados.remove(ro)) {
            this.costoRepuestos -= ro.getSubtotal();
            if (this.costoRepuestos < 0) this.costoRepuestos = 0;
        }
    }

    public void recalcularCostoRepuestos() {
        this.costoRepuestos = 0;
        for (RepuestoOrden ro : repuestosUsados)
            this.costoRepuestos += ro.getSubtotal();
    }

    @Override
    public String toString() {
        return "Mantenimiento{id=" + idMantenimiento + ", tipo='" + tipo + "', estado='" + estado + "'}";
    }
}
