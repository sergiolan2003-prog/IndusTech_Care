package com.industech.controllers;

import com.industech.models.Usuario;
import com.industech.services.UsuarioService;
import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    /** LOGIN — recibe nombre y contraseña */
    public Usuario login(String nombre, String contrasena) {
        return usuarioService.login(nombre, contrasena);
    }

    public boolean agregarUsuario(Usuario u)    { return usuarioService.agregarUsuario(u); }
    public boolean actualizarUsuario(Usuario u) { return usuarioService.actualizarUsuario(u); }
    public boolean eliminarUsuario(int id)      { return usuarioService.eliminarUsuario(id); }
    public Usuario buscarPorId(int id)          { return usuarioService.buscarPorId(id); }
    public List<Usuario> listarTodos()          { return usuarioService.listarTodos(); }
    public List<Usuario> listarPorRol(String r) { return usuarioService.listarPorRol(r); }
}
