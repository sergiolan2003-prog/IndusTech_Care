package com.industech.services;

import com.industech.models.Alerta;
import com.industech.models.Falla;
import com.industech.repositories.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FallaService — persistencia real en MySQL.
 * Tabla requerida:
 *   CREATE TABLE falla (
 *     id_falla    INT AUTO_INCREMENT PRIMARY KEY,
 *     fecha       DATE        NOT NULL,
 *     descripcion TEXT        NOT NULL,
 *     gravedad    VARCHAR(10) NOT NULL DEFAULT 'MEDIA'
 *   );
 */
public class FallaService {

    private final AlertaService alertaService = new AlertaService();

    public List<Falla> listarTodas() {
        List<Falla> lista = new ArrayList<>();
        String sql = "SELECT id_falla, fecha, descripcion, gravedad FROM falla ORDER BY id_falla";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar fallas: " + e.getMessage());
        }
        return lista;
    }

    public Falla buscarPorId(int id) {
        String sql = "SELECT id_falla, fecha, descripcion, gravedad FROM falla WHERE id_falla = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar falla: " + e.getMessage());
        }
        return null;
    }

    public boolean agregar(Falla f) {
        if (f.getDescripcion() == null || f.getDescripcion().isBlank()) return false;
        String sql = "INSERT INTO falla (fecha, descripcion, gravedad) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, f.getFecha() != null ? new java.sql.Date(f.getFecha().getTime()) : new java.sql.Date(System.currentTimeMillis()));
            ps.setString(2, f.getDescripcion().trim());
            ps.setString(3, f.getGravedad() != null ? f.getGravedad() : "MEDIA");
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) f.setIdFalla(keys.getInt(1));
                }
                // Generar alerta automática
                Alerta alerta = Alerta.crearAlerta(f.getIdFalla(),
                        "Nueva falla registrada: " + f.getDescripcion(), "FALLA");
                alertaService.agregar(alerta);
                alerta.enviarAlerta();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar falla: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar(Falla f) {
        if (f.getIdFalla() <= 0) return false;
        String sql = "UPDATE falla SET fecha=?, descripcion=?, gravedad=? WHERE id_falla=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, f.getFecha() != null ? new java.sql.Date(f.getFecha().getTime()) : new java.sql.Date(System.currentTimeMillis()));
            ps.setString(2, f.getDescripcion().trim());
            ps.setString(3, f.getGravedad());
            ps.setInt(4, f.getIdFalla());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar falla: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM falla WHERE id_falla = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar falla: " + e.getMessage());
        }
        return false;
    }

    public List<Falla> listarPorGravedad(String gravedad) {
        List<Falla> lista = new ArrayList<>();
        String sql = "SELECT id_falla, fecha, descripcion, gravedad FROM falla WHERE gravedad = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gravedad.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar fallas: " + e.getMessage());
        }
        return lista;
    }

    private Falla mapear(ResultSet rs) throws SQLException {
        return new Falla(
                rs.getInt("id_falla"),
                rs.getDate("fecha"),
                rs.getString("descripcion"),
                rs.getString("gravedad")
        );
    }
}
