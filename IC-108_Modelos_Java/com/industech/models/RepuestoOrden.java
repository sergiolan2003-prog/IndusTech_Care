package com.industech.models;

public class RepuestoOrden {

    private Repuesto repuesto;
    private int      cantidad;
    private double   costoUnitarioAlMomento;

    public RepuestoOrden(Repuesto repuesto, int cantidad) {
        this.repuesto               = repuesto;
        this.cantidad               = cantidad;
        this.costoUnitarioAlMomento = repuesto.getPrecioUnitario();
    }

    public Repuesto getRepuesto()    { return repuesto; }
    public int      getCantidad()    { return cantidad; }
    public void     setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getCostoUnitarioAlMomento() { return costoUnitarioAlMomento; }

    public double getSubtotal() {
        return cantidad * costoUnitarioAlMomento;
    }

    @Override
    public String toString() {
        return repuesto.getNombre() + " x" + cantidad;
    }
}
