package com.industech.services;

import com.industech.models.Usuario;
import com.industech.repositories.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UsuarioService — persistencia real en MySQL.
 *
 * Tabla requerida (ver industech_bd.sql):
 *   CREATE TABLE usuario (
 *     id_usuario  INT AUTO_INCREMENT PRIMARY KEY,
 *     nombre      VARCHAR(150) NOT NULL,
 *     direccion   VARCHAR(255) DEFAULT '',
 *     telefono    VARCHAR(20)  DEFAULT '',
 *     rol         VARCHAR(30)  NOT NULL DEFAULT 'CONSULTOR',
 *     contrasena  VARCHAR(255) NOT NULL DEFAULT '1234'
 *   );
 *
 */
public class UsuarioService {

    // ── LOGIN ────────────────────────────────────────────────────
    /**
     * Autentica al usuario por nombre y contraseña.
     * @return el Usuario encontrado, o null si las credenciales son incorrectas.
     */
    public Usuario login(String nombre, String contrasena) {
        String sql = "SELECT id_usuario, nombre, direccion, telefono, rol, contrasena " +
                     "FROM usuario WHERE nombre = ? AND contrasena = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre.trim());
            ps.setString(2, contrasena.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null;
    }

    // ── LISTAR TODOS ──────────────────────────────────────────────
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, direccion, telefono, rol, contrasena " +
                     "FROM usuario ORDER BY id_usuario";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // ── BUSCAR POR ID ─────────────────────────────────────────────
    public Usuario buscarPorId(int id) {
        String sql = "SELECT id_usuario, nombre, direccion, telefono, rol, contrasena " +
                     "FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null;
    }

    // ── AGREGAR ───────────────────────────────────────────────────
    public boolean agregarUsuario(Usuario u) {
        if (u.getNombre() == null || u.getNombre().isBlank()) return false;
        String sql = "INSERT INTO usuario (nombre, direccion, telefono, rol, contrasena) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre().trim());
            ps.setString(2, u.getDireccion() != null ? u.getDireccion().trim() : "");
            ps.setString(3, u.getTelefono()  != null ? u.getTelefono().trim()  : "");
            ps.setString(4, u.getRol()       != null ? u.getRol()              : "CONSULTOR");
            ps.setString(5, u.getContrasena() != null ? u.getContrasena()      : "1234");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) u.setIdUsuario(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
        }
        return false;
    }

    // ── ACTUALIZAR ────────────────────────────────────────────────
    public boolean actualizarUsuario(Usuario u) {
        if (u.getIdUsuario() <= 0) return false;
        String sql = "UPDATE usuario SET nombre=?, direccion=?, telefono=?, rol=?, contrasena=? " +
                     "WHERE id_usuario=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre().trim());
            ps.setString(2, u.getDireccion() != null ? u.getDireccion().trim() : "");
            ps.setString(3, u.getTelefono()  != null ? u.getTelefono().trim()  : "");
            ps.setString(4, u.getRol());
            ps.setString(5, u.getContrasena() != null ? u.getContrasena()      : "1234");
            ps.setInt(6, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
        return false;
    }

    // ── ELIMINAR ──────────────────────────────────────────────────
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
        return false;
    }

    // ── LISTAR POR ROL ────────────────────────────────────────────
    public List<Usuario> listarPorRol(String rol) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, direccion, telefono, rol, contrasena " +
                     "FROM usuario WHERE rol = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar usuarios por rol: " + e.getMessage());
        }
        return lista;
    }

    // ── MAPEO ─────────────────────────────────────────────────────
    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getString("rol"),
                rs.getString("contrasena")
        );
    }
}
