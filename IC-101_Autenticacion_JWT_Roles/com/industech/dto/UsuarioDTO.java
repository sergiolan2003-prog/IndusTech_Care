package com.industech.dto;

/**
 * DTO de Usuario — expone solo nombre y rol (sin datos sensibles).
 */
public class UsuarioDTO {
    private int    idUsuario;
    private String nombre;
    private String rol;

    public UsuarioDTO() {}

    public UsuarioDTO(int idUsuario, String nombre, String rol) {
        this.idUsuario = idUsuario;
        this.nombre    = nombre;
        this.rol       = rol;
    }

    public int    getIdUsuario()        { return idUsuario; }
    public void   setIdUsuario(int v)   { this.idUsuario = v; }
    public String getNombre()           { return nombre; }
    public void   setNombre(String v)   { this.nombre = v; }
    public String getRol()              { return rol; }
    public void   setRol(String v)      { this.rol = v; }
}
