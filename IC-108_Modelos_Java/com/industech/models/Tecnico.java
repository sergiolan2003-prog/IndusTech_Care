package com.industech.models;

public class Tecnico {
    private int idTecnico;
    private String nombre;
    private String especialidad;
    private String telefono;

    public Tecnico() {}

    public Tecnico(int idTecnico, String nombre, String especialidad, String telefono) {
        this.idTecnico = idTecnico;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    public int getIdTecnico() { return idTecnico; }
    public void setIdTecnico(int idTecnico) { this.idTecnico = idTecnico; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String diagnosticarFalla(int idFalla) {
        return "Diagnóstico para falla #" + idFalla;
    }

    @Override
    public String toString() {
        return "Tecnico{id=" + idTecnico + ", nombre='" + nombre + "', especialidad='" + especialidad + "'}";
    }
}
