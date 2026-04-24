package com.industech.services;

import com.industech.models.Alerta;
import com.industech.repositories.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AlertaService — persistencia real en MySQL.
 *

 */
public class AlertaService {

    // ── AGREGAR ───────────────────────────────────────────────────
    public boolean agregar(Alerta a) {
        String sql = "INSERT INTO alerta (mensaje, tipo, activa) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getMensaje());
            ps.setString(2, a.getTipo());
            ps.setBoolean(3, a.isActiva());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    // Alerta no tiene setId, pero guardamos en BD correctamente
                    if (keys.next()) System.out.println("Alerta guardada id=" + keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar alerta: " + e.getMessage());
        }
        return false;
    }

    // ── BUSCAR POR ID ─────────────────────────────────────────────
    public Alerta buscarPorId(int id) {
        String sql = "SELECT id_alerta, mensaje, tipo, activa FROM alerta WHERE id_alerta = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar alerta: " + e.getMessage());
        }
        return null;
    }

    // ── DESACTIVAR ────────────────────────────────────────────────
    public boolean desactivar(int id) {
        String sql = "UPDATE alerta SET activa = 0 WHERE id_alerta = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al desactivar alerta: " + e.getMessage());
        }
        return false;
    }

    // ── ACTUALIZAR ────────────────────────────────────────────────
    public boolean actualizar(int id, String nuevoMensaje, String nuevoTipo) {
        String sql = "UPDATE alerta SET mensaje=?, tipo=? WHERE id_alerta=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoMensaje);
            ps.setString(2, nuevoTipo);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar alerta: " + e.getMessage());
        }
        return false;
    }

    // ── LISTAR ────────────────────────────────────────────────────
    public List<Alerta> listarTodas() {
        List<Alerta> lista = new ArrayList<>();
        String sql = "SELECT id_alerta, mensaje, tipo, activa FROM alerta ORDER BY id_alerta DESC";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar alertas: " + e.getMessage());
        }
        return lista;
    }

    public List<Alerta> listarActivas() {
        List<Alerta> lista = new ArrayList<>();
        String sql = "SELECT id_alerta, mensaje, tipo, activa FROM alerta WHERE activa = 1 ORDER BY id_alerta DESC";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar alertas activas: " + e.getMessage());
        }
        return lista;
    }

    public void enviarTodas() {
        listarActivas().forEach(Alerta::enviarAlerta);
    }

    // ── MAPEO ─────────────────────────────────────────────────────
    private Alerta mapear(ResultSet rs) throws SQLException {
        Alerta a = new Alerta(
                rs.getInt("id_alerta"),
                rs.getString("mensaje"),
                rs.getString("tipo"));
        if (!rs.getBoolean("activa")) a.desactivarAlerta();
        return a;
    }
}
