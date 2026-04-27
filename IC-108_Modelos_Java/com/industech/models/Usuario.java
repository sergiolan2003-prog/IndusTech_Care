package com.industech.models;

public class Usuario {

    private int    idUsuario;
    private String nombre;
    private String direccion;
    private String telefono;
    private String rol;        // "ADMIN", "JEFE_MANTENIMIENTO", "TECNICO", "CONSULTOR"
    private String contrasena; // campo añadido para login real

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String direccion,
                   String telefono, String rol) {
        this.idUsuario = idUsuario;
        this.nombre    = nombre;
        this.direccion = direccion;
        this.telefono  = telefono;
        this.rol       = rol;
    }

    public Usuario(int idUsuario, String nombre, String direccion,
                   String telefono, String rol, String contrasena) {
        this(idUsuario, nombre, direccion, telefono, rol);
        this.contrasena = contrasena;
    }

    public int    getIdUsuario()  { return idUsuario; }
    public void   setIdUsuario(int v) { this.idUsuario = v; }

    public String getNombre()     { return nombre; }
    public void   setNombre(String v) { this.nombre = v; }

    public String getDireccion()  { return direccion; }
    public void   setDireccion(String v) { this.direccion = v; }

    public String getTelefono()   { return telefono; }
    public void   setTelefono(String v) { this.telefono = v; }

    public String getRol()        { return rol; }
    public void   setRol(String v) { this.rol = v; }

    public String getContrasena() { return contrasena; }
    public void   setContrasena(String v) { this.contrasena = v; }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario + ", nombre='" + nombre + "', rol='" + rol + "'}";
    }
}
