package com.industech.models;

public class Alerta {

    private int     id;
    private String  mensaje;
    private String  tipo;    // Ej: "FALLA", "MANTENIMIENTO", "STOCK"
    private boolean activa;

    public Alerta(int id, String mensaje, String tipo) {
        this.id     = id;
        this.mensaje = mensaje;
        this.tipo   = tipo;
        this.activa = true;
    }

    public static Alerta crearAlerta(int id, String mensaje, String tipo) {
        return new Alerta(id, mensaje, tipo);
    }

    public void mostrarAlerta() {
        System.out.println("ID:     " + id);
        System.out.println("Mensaje:" + mensaje);
        System.out.println("Tipo:   " + tipo);
        System.out.println("Activa: " + activa);
    }

    public void actualizarAlerta(String nuevoMensaje, String nuevoTipo) {
        this.mensaje = nuevoMensaje;
        this.tipo    = nuevoTipo;
    }

    public void desactivarAlerta() {
        this.activa = false;
        System.out.println("Alerta desactivada");
    }

    public void enviarAlerta() {
        if (activa) {
            System.out.println("Alerta: " + mensaje + " | Tipo: " + tipo);
        } else {
            System.out.println("La alerta no está activa");
        }
    }

    public int     getId()      { return id; }
    public String  getMensaje() { return mensaje; }
    public String  getTipo()    { return tipo; }
    public boolean isActiva()   { return activa; }

    @Override
    public String toString() {
        return "Alerta{id=" + id + ", tipo='" + tipo + "', activa=" + activa + "}";
    }
}