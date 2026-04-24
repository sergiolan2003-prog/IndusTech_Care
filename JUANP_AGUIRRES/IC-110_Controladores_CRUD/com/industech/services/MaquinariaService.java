package com.industech.services;

import com.industech.models.Maquinaria;
import com.industech.repositories.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MaquinariaService — persistencia real en MySQL.
 * Tabla requerida:
 *   CREATE TABLE maquinaria (
 *     id_maquinaria INT AUTO_INCREMENT PRIMARY KEY,
 *     tipo          VARCHAR(100) NOT NULL,
 *     fecha         DATE         NOT NULL,
 *     descripcion   TEXT,
 *     estado        VARCHAR(50)  NOT NULL DEFAULT 'OPERATIVO'
 *   );
 */
public class MaquinariaService {

    // ── LISTAR ────────────────────────────────────────────
    public List<Maquinaria> listarTodas() {
        List<Maquinaria> lista = new ArrayList<>();
        String sql = "SELECT id_maquinaria, tipo, fecha, descripcion, estado FROM maquinaria ORDER BY id_maquinaria";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar maquinaria: " + e.getMessage());
        }
        return lista;
    }

    // ── BUSCAR POR ID ─────────────────────────────────────
    public Maquinaria buscarPorId(int id) {
        String sql = "SELECT id_maquinaria, tipo, fecha, descripcion, estado FROM maquinaria WHERE id_maquinaria = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar maquinaria: " + e.getMessage());
        }
        return null;
    }

    // ── AGREGAR ───────────────────────────────────────────
    public boolean agregar(Maquinaria m) {
        if (m.getTipo() == null || m.getTipo().isBlank()) return false;
        if (m.getDescripcion() == null || m.getDescripcion().isBlank()) return false;
        String sql = "INSERT INTO maquinaria (tipo, fecha, descripcion, estado) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getTipo().trim());
            ps.setDate(2, m.getFecha() != null ? new java.sql.Date(m.getFecha().getTime()) : new java.sql.Date(System.currentTimeMillis()));
            ps.setString(3, m.getDescripcion().trim());
            ps.setString(4, m.getEstado() != null ? m.getEstado() : "OPERATIVO");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) m.setIdMaquinaria(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar maquinaria: " + e.getMessage());
        }
        return false;
    }

    // ── ACTUALIZAR ────────────────────────────────────────
    public boolean actualizar(Maquinaria m) {
        if (m.getIdMaquinaria() <= 0) return false;
        String sql = "UPDATE maquinaria SET tipo=?, fecha=?, descripcion=?, estado=? WHERE id_maquinaria=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getTipo().trim());
            ps.setDate(2, m.getFecha() != null ? new java.sql.Date(m.getFecha().getTime()) : new java.sql.Date(System.currentTimeMillis()));
            ps.setString(3, m.getDescripcion().trim());
            ps.setString(4, m.getEstado());
            ps.setInt(5, m.getIdMaquinaria());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar maquinaria: " + e.getMessage());
        }
        return false;
    }

    // ── ELIMINAR ──────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "DELETE FROM maquinaria WHERE id_maquinaria = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar maquinaria: " + e.getMessage());
        }
        return false;
    }

    // ── CAMBIAR ESTADO ────────────────────────────────────
    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE maquinaria SET estado=? WHERE id_maquinaria=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al cambiar estado: " + e.getMessage());
        }
        return false;
    }

    // ── LISTAR POR ESTADO ─────────────────────────────────
    public List<Maquinaria> listarPorEstado(String estado) {
        List<Maquinaria> lista = new ArrayList<>();
        String sql = "SELECT id_maquinaria, tipo, fecha, descripcion, estado FROM maquinaria WHERE estado = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar por estado: " + e.getMessage());
        }
        return lista;
    }

    // ── LISTAR POR TIPO ───────────────────────────────────
    public List<Maquinaria> listarPorTipo(String tipo) {
        List<Maquinaria> lista = new ArrayList<>();
        String sql = "SELECT id_maquinaria, tipo, fecha, descripcion, estado FROM maquinaria WHERE tipo = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar por tipo: " + e.getMessage());
        }
        return lista;
    }

    // ── FILTRAR ───────────────────────────────────────────
    public List<Maquinaria> filtrar(String estado, String tipo, String textoBusqueda) {
        List<Maquinaria> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT id_maquinaria, tipo, fecha, descripcion, estado FROM maquinaria WHERE 1=1");

        boolean filtrarEstado = estado != null && !estado.equals("TODOS");
        boolean filtrarTipo   = tipo   != null && !tipo.equals("TODOS");
        boolean filtrarTexto  = textoBusqueda != null && !textoBusqueda.isBlank();

        if (filtrarEstado) sql.append(" AND estado = ?");
        if (filtrarTipo)   sql.append(" AND tipo   = ?");
        if (filtrarTexto)  sql.append(" AND (descripcion LIKE ? OR tipo LIKE ?)");

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int idx = 1;
            if (filtrarEstado) ps.setString(idx++, estado);
            if (filtrarTipo)   ps.setString(idx++, tipo);
            if (filtrarTexto)  {
                String like = "%" + textoBusqueda + "%";
                ps.setString(idx++, like);
                ps.setString(idx,   like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar maquinaria: " + e.getMessage());
        }
        return lista;
    }

    // ── MAPEO ResultSet → Maquinaria ─────────────────────
    private Maquinaria mapear(ResultSet rs) throws SQLException {
        return new Maquinaria(
                rs.getInt("id_maquinaria"),
                rs.getString("tipo"),
                rs.getDate("fecha"),
                rs.getString("descripcion"),
                rs.getString("estado")
        );
    }
}
