package com.industech.security;

import com.industech.models.Usuario;
import com.industech.services.UsuarioService;

/**
 * LoginManager — gestiona la sesión activa del sistema.
 *
 */
public class LoginManager {

    private final UsuarioService usuarioService = new UsuarioService();
    private Usuario usuarioActual = null;

    /**
     * Intenta iniciar sesión con nombre y contraseña.
     * @return true si las credenciales son válidas.
     */
    public boolean iniciarSesion(String nombre, String contrasena) {
        Usuario u = usuarioService.login(nombre, contrasena);
        if (u != null) {
            usuarioActual = u;
            System.out.println("✅ Sesión iniciada: " + u.getNombre() + " [" + u.getRol() + "]");
            return true;
        }
        System.out.println("❌ Credenciales incorrectas para: " + nombre);
        return false;
    }

    public void cerrarSesion() {
        System.out.println("👋 Sesión cerrada: " +
                (usuarioActual != null ? usuarioActual.getNombre() : ""));
        usuarioActual = null;
    }

    public boolean haySesionActiva()  { return usuarioActual != null; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public String  getRolActual()     { return usuarioActual != null ? usuarioActual.getRol() : null; }

    public boolean tieneRol(String rol) {
        return haySesionActiva() && usuarioActual.getRol().equalsIgnoreCase(rol);
    }
}
