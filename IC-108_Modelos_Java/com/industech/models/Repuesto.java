package com.industech.models;

public class Repuesto {

    private int    idRepuesto;
    private String nombre;
    private String referencia;
    private String unidad;
    private double precioUnitario;
    private int    stockDisponible;

    public Repuesto() {}

    public Repuesto(int idRepuesto, String nombre, String referencia,
                    String unidad, double precioUnitario, int stockDisponible) {
        this.idRepuesto      = idRepuesto;
        this.nombre          = nombre;
        this.referencia      = referencia;
        this.unidad          = unidad;
        this.precioUnitario  = precioUnitario;
        this.stockDisponible = stockDisponible;
    }

    public int    getIdRepuesto()       { return idRepuesto; }
    public void   setIdRepuesto(int v)  { this.idRepuesto = v; }

    public String getNombre()           { return nombre; }
    public void   setNombre(String v)   { this.nombre = v; }

    public String getReferencia()       { return referencia; }
    public void   setReferencia(String v) { this.referencia = v; }

    public String getUnidad()           { return unidad; }
    public void   setUnidad(String v)   { this.unidad = v; }

    public double getPrecioUnitario()   { return precioUnitario; }
    public void   setPrecioUnitario(double v) { this.precioUnitario = v; }

    public int    getStockDisponible()  { return stockDisponible; }
    public void   setStockDisponible(int v) { this.stockDisponible = v; }

    public boolean descontarStock(int cantidad) {
        if (cantidad > stockDisponible) return false;
        this.stockDisponible -= cantidad;
        return true;
    }

    @Override
    public String toString() {
        return nombre + " [" + referencia + "]";
    }
}
