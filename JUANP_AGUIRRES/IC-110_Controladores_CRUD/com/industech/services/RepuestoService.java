package com.industech.services;

import com.industech.models.Alerta;
import com.industech.models.Repuesto;
import com.industech.repositories.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RepuestoService — persistencia real en MySQL.
 * Tabla requerida:
 *   CREATE TABLE repuesto (
 *     id_repuesto      INT AUTO_INCREMENT PRIMARY KEY,
 *     nombre           VARCHAR(150) NOT NULL,
 *     referencia       VARCHAR(100) NOT NULL UNIQUE,
 *     unidad           VARCHAR(50)  NOT NULL,
 *     precio_unitario  DECIMAL(12,2) NOT NULL DEFAULT 0,
 *     stock_disponible INT          NOT NULL DEFAULT 0
 *   );
 */
public class RepuestoService {

    private final AlertaService alertaService = new AlertaService();

    private static final int UMBRAL_STOCK_BAJO = 5;

    public List<Repuesto> listarTodos() {
        List<Repuesto> lista = new ArrayList<>();
        String sql = "SELECT id_repuesto, nombre, referencia, unidad, precio_unitario, stock_disponible FROM repuesto ORDER BY id_repuesto";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar repuestos: " + e.getMessage());
        }
        return lista;
    }

    public Repuesto buscarPorId(int id) {
        String sql = "SELECT id_repuesto, nombre, referencia, unidad, precio_unitario, stock_disponible FROM repuesto WHERE id_repuesto = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar repuesto: " + e.getMessage());
        }
        return null;
    }

    public boolean agregar(Repuesto r) {
        if (r.getNombre() == null    || r.getNombre().isBlank())    return false;
        if (r.getReferencia() == null || r.getReferencia().isBlank()) return false;
        if (r.getPrecioUnitario() < 0 || r.getStockDisponible() < 0) return false;

        String sql = "INSERT INTO repuesto (nombre, referencia, unidad, precio_unitario, stock_disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getNombre().trim());
            ps.setString(2, r.getReferencia().trim());
            ps.setString(3, r.getUnidad() != null ? r.getUnidad().trim() : "unidad");
            ps.setDouble(4, r.getPrecioUnitario());
            ps.setInt(5, r.getStockDisponible());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) r.setIdRepuesto(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar repuesto: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar(Repuesto r) {
        if (r.getIdRepuesto() <= 0) return false;
        String sql = "UPDATE repuesto SET nombre=?, referencia=?, unidad=?, precio_unitario=?, stock_disponible=? WHERE id_repuesto=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre().trim());
            ps.setString(2, r.getReferencia().trim());
            ps.setString(3, r.getUnidad().trim());
            ps.setDouble(4, r.getPrecioUnitario());
            ps.setInt(5, r.getStockDisponible());
            ps.setInt(6, r.getIdRepuesto());
            boolean ok = ps.executeUpdate() > 0;
            if (ok && r.getStockDisponible() < UMBRAL_STOCK_BAJO) {
                Alerta alerta = Alerta.crearAlerta(r.getIdRepuesto(),
                        "Stock bajo: " + r.getNombre() + " solo tiene " + r.getStockDisponible() + " unidades", "STOCK");
                alertaService.agregar(alerta);
                alerta.enviarAlerta();
            }
            return ok;
        } catch (SQLException e) {
            System.err.println("Error al actualizar repuesto: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM repuesto WHERE id_repuesto = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar repuesto: " + e.getMessage());
        }
        return false;
    }

    public List<Repuesto> buscarPorNombre(String texto) {
        List<Repuesto> lista = new ArrayList<>();
        String sql = "SELECT id_repuesto, nombre, referencia, unidad, precio_unitario, stock_disponible FROM repuesto WHERE nombre LIKE ? OR referencia LIKE ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + texto + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar repuesto por nombre: " + e.getMessage());
        }
        return lista;
    }

    private Repuesto mapear(ResultSet rs) throws SQLException {
        return new Repuesto(
                rs.getInt("id_repuesto"),
                rs.getString("nombre"),
                rs.getString("referencia"),
                rs.getString("unidad"),
                rs.getDouble("precio_unitario"),
                rs.getInt("stock_disponible")
        );
    }
}
