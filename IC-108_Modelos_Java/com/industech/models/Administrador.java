package com.industech.models;

public class Administrador {
    private int idAdministrador;
    private String nombre;
    private String direccion;
    private String telefono;

    public Administrador() {}

    public Administrador(int idAdministrador, String nombre, String direccion, String telefono) {
        this.idAdministrador = idAdministrador;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getIdAdministrador() { return idAdministrador; }
    public void setIdAdministrador(int id) { this.idAdministrador = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Administrador{id=" + idAdministrador + ", nombre='" + nombre + "'}";
    }
}
