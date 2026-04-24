package com.industech.services;

import com.industech.models.Alerta;
import com.industech.models.Mantenimiento;
import com.industech.models.Repuesto;
import com.industech.models.RepuestoOrden;
import com.industech.models.Tecnico;
import com.industech.repositories.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * MantenimientoService — persistencia 100 % en MySQL.
 *
 *
 * Tablas requeridas: mantenimiento, mantenimiento_tecnico,
 *                    mantenimiento_repuesto  (ver industech_bd.sql)
 */
public class MantenimientoService {

    private final AlertaService alertaService = new AlertaService();

    // ── LISTAR TODOS ──────────────────────────────────────────────
    public List<Mantenimiento> listarTodos() {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT id_mantenimiento, tipo, fecha, descripcion, estado, " +
                     "hora_inicio, hora_fin, costo_mano_obra, costo_repuestos, id_maquinaria " +
                     "FROM mantenimiento ORDER BY id_mantenimiento";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar mantenimientos: " + e.getMessage());
        }
        return lista;
    }

    // ── BUSCAR POR ID ─────────────────────────────────────────────
    public Mantenimiento buscarPorId(int id) {
        String sql = "SELECT id_mantenimiento, tipo, fecha, descripcion, estado, " +
                     "hora_inicio, hora_fin, costo_mano_obra, costo_repuestos, id_maquinaria " +
                     "FROM mantenimiento WHERE id_mantenimiento = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar mantenimiento: " + e.getMessage());
        }
        return null;
    }

    // ── AGREGAR ───────────────────────────────────────────────────
    public boolean agregar(Mantenimiento m) {
        if (m.getDescripcion() == null || m.getDescripcion().isBlank()) return false;
        String sql = "INSERT INTO mantenimiento " +
                     "(tipo, fecha, descripcion, estado, hora_inicio, hora_fin, " +
                     " costo_mano_obra, costo_repuestos, id_maquinaria) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getTipo() != null ? m.getTipo() : "Preventivo");
            ps.setDate(2, m.getFecha() != null
                    ? new java.sql.Date(m.getFecha().getTime())
                    : new java.sql.Date(System.currentTimeMillis()));
            ps.setString(3, m.getDescripcion().trim());
            ps.setString(4, m.getEstado() != null ? m.getEstado() : "ACTIVO");
            ps.setString(5, m.getHoraInicio() != null ? m.getHoraInicio().toString() : null);
            ps.setString(6, m.getHoraFin()    != null ? m.getHoraFin().toString()    : null);
            ps.setDouble(7, m.getCostoManoObra());
            ps.setDouble(8, m.getCostoRepuestos());
            ps.setInt(9, m.getIdMaquinaria());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) m.setIdMantenimiento(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar mantenimiento: " + e.getMessage());
        }
        return false;
    }

    // ── ACTUALIZAR ────────────────────────────────────────────────
    public boolean actualizar(Mantenimiento m) {
        if (m.getIdMantenimiento() <= 0) return false;
        String sql = "UPDATE mantenimiento SET tipo=?, fecha=?, descripcion=?, estado=?, " +
                     "hora_inicio=?, hora_fin=?, costo_mano_obra=?, costo_repuestos=?, " +
                     "id_maquinaria=? WHERE id_mantenimiento=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getTipo());
            ps.setDate(2, m.getFecha() != null
                    ? new java.sql.Date(m.getFecha().getTime())
                    : new java.sql.Date(System.currentTimeMillis()));
            ps.setString(3, m.getDescripcion().trim());
            ps.setString(4, m.getEstado());
            ps.setString(5, m.getHoraInicio() != null ? m.getHoraInicio().toString() : null);
            ps.setString(6, m.getHoraFin()    != null ? m.getHoraFin().toString()    : null);
            ps.setDouble(7, m.getCostoManoObra());
            ps.setDouble(8, m.getCostoRepuestos());
            ps.setInt(9, m.getIdMaquinaria());
            ps.setInt(10, m.getIdMantenimiento());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar mantenimiento: " + e.getMessage());
        }
        return false;
    }

    // ── ELIMINAR ──────────────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mantenimiento WHERE id_mantenimiento = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar mantenimiento: " + e.getMessage());
        }
        return false;
    }

    // ── INICIAR / FINALIZAR ───────────────────────────────────────
    public boolean iniciar(int id) {
        return cambiarEstado(id, "ACTIVO");
    }

    public boolean finalizar(int id) {
        return cambiarEstado(id, "FINALIZADO");
    }

    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE mantenimiento SET estado=? WHERE id_mantenimiento=?";
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

    // ── FILTRAR ───────────────────────────────────────────────────
    public List<Mantenimiento> filtrar(String estado, String tipo, String textoBusqueda) {
        List<Mantenimiento> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT id_mantenimiento, tipo, fecha, descripcion, estado, " +
                "hora_inicio, hora_fin, costo_mano_obra, costo_repuestos, id_maquinaria " +
                "FROM mantenimiento WHERE 1=1");

        boolean filtrarEstado = estado != null && !estado.equals("TODOS");
        boolean filtrarTipo   = tipo   != null && !tipo.equals("TODOS");
        boolean filtrarTexto  = textoBusqueda != null && !textoBusqueda.isBlank();

        if (filtrarEstado) sql.append(" AND estado = ?");
        if (filtrarTipo)   sql.append(" AND tipo   = ?");
        if (filtrarTexto)  sql.append(" AND descripcion LIKE ?");

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int idx = 1;
            if (filtrarEstado) ps.setString(idx++, estado);
            if (filtrarTipo)   ps.setString(idx++, tipo);
            if (filtrarTexto)  ps.setString(idx,   "%" + textoBusqueda + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al filtrar mantenimientos: " + e.getMessage());
        }
        return lista;
    }

    public List<Mantenimiento> listarPorEstado(String estado) {
        return filtrar(estado, null, null);
    }

    public List<Mantenimiento> listarPorTipo(String tipo) {
        return filtrar(null, tipo, null);
    }

    // ── TÉCNICOS ASIGNADOS ────────────────────────────────────────
    public boolean asignarTecnico(int idMantenimiento, Tecnico tecnico) {
        String sql = "INSERT IGNORE INTO mantenimiento_tecnico " +
                     "(id_mantenimiento, id_tecnico) VALUES (?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMantenimiento);
            ps.setInt(2, tecnico.getIdTecnico());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al asignar técnico: " + e.getMessage());
        }
        return false;
    }

    public boolean desasignarTecnico(int idMantenimiento, int idTecnico) {
        String sql = "DELETE FROM mantenimiento_tecnico " +
                     "WHERE id_mantenimiento=? AND id_tecnico=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMantenimiento);
            ps.setInt(2, idTecnico);
            boolean ok = ps.executeUpdate() > 0;
            if (ok && getTecnicosDeOrden(idMantenimiento).isEmpty()) {
                Alerta alerta = Alerta.crearAlerta(idMantenimiento,
                        "Orden #" + idMantenimiento + " quedó sin técnico asignado",
                        "MANTENIMIENTO");
                alertaService.agregar(alerta);
                alerta.enviarAlerta();
            }
            return ok;
        } catch (SQLException e) {
            System.err.println("Error al desasignar técnico: " + e.getMessage());
        }
        return false;
    }

    public List<Tecnico> getTecnicosDeOrden(int idMantenimiento) {
        List<Tecnico> lista = new ArrayList<>();
        String sql = "SELECT t.id_tecnico, t.nombre, t.especialidad, t.telefono " +
                     "FROM tecnico t " +
                     "JOIN mantenimiento_tecnico mt ON t.id_tecnico = mt.id_tecnico " +
                     "WHERE mt.id_mantenimiento = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMantenimiento);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(new Tecnico(
                        rs.getInt("id_tecnico"), rs.getString("nombre"),
                        rs.getString("especialidad"), rs.getString("telefono")));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener técnicos de orden: " + e.getMessage());
        }
        return lista;
    }

    // ── REPUESTOS DE ORDEN ────────────────────────────────────────
    public boolean enlazarRepuesto(int idMantenimiento, Repuesto repuesto, int cantidad) {
        // Descontar stock
        String sqlStock = "UPDATE repuesto SET stock_disponible = stock_disponible - ? " +
                          "WHERE id_repuesto = ? AND stock_disponible >= ?";
        String sqlLink  = "INSERT INTO mantenimiento_repuesto " +
                          "(id_mantenimiento, id_repuesto, cantidad) VALUES (?, ?, ?) " +
                          "ON DUPLICATE KEY UPDATE cantidad = cantidad + ?";
        try (Connection con = ConexionDB.getConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(sqlStock)) {
                ps1.setInt(1, cantidad);
                ps1.setInt(2, repuesto.getIdRepuesto());
                ps1.setInt(3, cantidad);
                if (ps1.executeUpdate() == 0) { con.rollback(); return false; }
            }
            try (PreparedStatement ps2 = con.prepareStatement(sqlLink)) {
                ps2.setInt(1, idMantenimiento);
                ps2.setInt(2, repuesto.getIdRepuesto());
                ps2.setInt(3, cantidad);
                ps2.setInt(4, cantidad);
                ps2.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al enlazar repuesto: " + e.getMessage());
        }
        return false;
    }

    public boolean quitarRepuesto(int idMantenimiento, RepuestoOrden ro) {
        // Devolver stock y borrar la relación en BD
        String sqlStock = "UPDATE repuesto SET stock_disponible = stock_disponible + ? " +
                          "WHERE id_repuesto = ?";
        String sqlDel   = "DELETE FROM mantenimiento_repuesto " +
                          "WHERE id_mantenimiento=? AND id_repuesto=?";
        try (Connection con = ConexionDB.getConexion()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(sqlStock)) {
                ps1.setInt(1, ro.getCantidad());
                ps1.setInt(2, ro.getRepuesto().getIdRepuesto());
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = con.prepareStatement(sqlDel)) {
                ps2.setInt(1, idMantenimiento);
                ps2.setInt(2, ro.getRepuesto().getIdRepuesto());
                ps2.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al quitar repuesto: " + e.getMessage());
        }
        return false;
    }

    public List<RepuestoOrden> getRepuestosDeOrden(int idMantenimiento) {
        List<RepuestoOrden> lista = new ArrayList<>();
        String sql = "SELECT r.id_repuesto, r.nombre, r.referencia, r.unidad, " +
                     "r.precio_unitario, r.stock_disponible, mr.cantidad " +
                     "FROM repuesto r " +
                     "JOIN mantenimiento_repuesto mr ON r.id_repuesto = mr.id_repuesto " +
                     "WHERE mr.id_mantenimiento = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMantenimiento);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Repuesto r = new Repuesto(
                            rs.getInt("id_repuesto"), rs.getString("nombre"),
                            rs.getString("referencia"), rs.getString("unidad"),
                            rs.getDouble("precio_unitario"), rs.getInt("stock_disponible"));
                    lista.add(new RepuestoOrden(r, rs.getInt("cantidad")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener repuestos de orden: " + e.getMessage());
        }
        return lista;
    }

    // ── COSTOS ────────────────────────────────────────────────────
    public boolean actualizarCostos(int idMantenimiento,
                                    double costoManoObra, double costoRepuestos) {
        String sql = "UPDATE mantenimiento SET costo_mano_obra=?, costo_repuestos=? " +
                     "WHERE id_mantenimiento=?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, costoManoObra);
            ps.setDouble(2, costoRepuestos);
            ps.setInt(3, idMantenimiento);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar costos: " + e.getMessage());
        }
        return false;
    }

    public double getCostoTotalGeneral() {
        String sql = "SELECT SUM(costo_mano_obra + costo_repuestos) FROM mantenimiento";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("Error al calcular costo total: " + e.getMessage());
        }
        return 0;
    }

    public Map<Integer, Double> getCostosPorMaquina() {
        Map<Integer, Double> mapa = new LinkedHashMap<>();
        String sql = "SELECT id_maquinaria, SUM(costo_mano_obra + costo_repuestos) AS total " +
                     "FROM mantenimiento GROUP BY id_maquinaria";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) mapa.put(rs.getInt("id_maquinaria"), rs.getDouble("total"));
        } catch (SQLException e) {
            System.err.println("Error al agrupar costos por máquina: " + e.getMessage());
        }
        return mapa;
    }

    public Map<String, Double> getCostosPorTipo() {
        Map<String, Double> mapa = new LinkedHashMap<>();
        String sql = "SELECT tipo, SUM(costo_mano_obra + costo_repuestos) AS total " +
                     "FROM mantenimiento GROUP BY tipo";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) mapa.put(rs.getString("tipo"), rs.getDouble("total"));
        } catch (SQLException e) {
            System.err.println("Error al agrupar costos por tipo: " + e.getMessage());
        }
        return mapa;
    }

    // ── MAPEO ResultSet → Mantenimiento ──────────────────────────
    private Mantenimiento mapear(ResultSet rs) throws SQLException {
        Mantenimiento m = new Mantenimiento(
                rs.getInt("id_mantenimiento"),
                rs.getString("tipo"),
                rs.getDate("fecha"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                rs.getDouble("costo_mano_obra"),
                rs.getDouble("costo_repuestos"),
                rs.getInt("id_maquinaria"));

        String hi = rs.getString("hora_inicio");
        String hf = rs.getString("hora_fin");
        if (hi != null) m.setHoraInicio(java.time.LocalTime.parse(hi));
        if (hf != null) m.setHoraFin(java.time.LocalTime.parse(hf));
        return m;
    }
}
