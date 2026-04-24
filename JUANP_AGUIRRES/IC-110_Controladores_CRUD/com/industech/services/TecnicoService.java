package com.industech.services;

import com.industech.models.Tecnico;
import com.industech.repositories.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TecnicoService — persistencia real en MySQL.
 * Tabla requerida:
 *   CREATE TABLE tecnico (
 *     id_tecnico    INT AUTO_INCREMENT PRIMARY KEY,
 *     nombre        VARCHAR(150) NOT NULL,
 *     especialidad  VARCHAR(100) NOT NULL,
 *     telefono      VARCHAR(20)  NOT NULL
 *   );
 */
public class TecnicoService {

    public List<Tecnico> listarTodos() {
        List<Tecnico> lista = new ArrayList<>();
        String sql = "SELECT id_tecnico, nombre, especialidad, telefono FROM tecnico ORDER BY id_tecnico";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar técnicos: " + e.getMessage());
        }
        return lista;
    }

    public Tecnico buscarPorId(int id) {
        String sql = "SELECT id_tecnico, nombre, especialidad, telefono FROM tecnico WHERE id_tecnico = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar técnico: " + e.getMessage());
        }
        return null;
    }

    public boolean agregar(Tecnico t) {
        if (t.getNombre() == null || t.getNombre().isBlank())           return false;
        if (t.getEspecialidad() == null || t.getEspecialidad().isBlank()) return false;
        if (t.getTelefono() == null || !t.getTelefono().matches("\\d{7,15}")) return false;

        String sql = "INSERT INTO tecnico (nombre, especialidad, telefono) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombre().trim());
            ps.setString(2, t.getEspecialidad().trim());
            ps.setString(3, t.getTelefono().trim());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) t.setIdTecnico(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar técnico: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar(Tecnico t) {
        if (t.getIdTecnico() <= 0) return false;
        String sql = "UPDATE tecnico SET nombre=?, especialidad=?, telefono=? WHERE id_tecnico=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombre().trim());
            ps.setString(2, t.getEspecialidad().trim());
            ps.setString(3, t.getTelefono().trim());
            ps.setInt(4, t.getIdTecnico());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar técnico: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM tecnico WHERE id_tecnico = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar técnico: " + e.getMessage());
        }
        return false;
    }

    public List<Tecnico> listarPorEspecialidad(String especialidad) {
        List<Tecnico> lista = new ArrayList<>();
        String sql = "SELECT id_tecnico, nombre, especialidad, telefono FROM tecnico WHERE especialidad = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, especialidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar técnicos: " + e.getMessage());
        }
        return lista;
    }

    private Tecnico mapear(ResultSet rs) throws SQLException {
        return new Tecnico(
                rs.getInt("id_tecnico"),
                rs.getString("nombre"),
                rs.getString("especialidad"),
                rs.getString("telefono")
        );
    }
}
