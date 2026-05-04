package com.industech.view;

import com.industech.controllers.MaquinariaController;
import com.industech.models.Maquinaria;
import com.industech.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Vista: Visualización del Estado de los Equipos
 * Muestra tarjetas resumen por estado + tabla filtrable con colores.
 * Rol objetivo: TECNICO (también accesible para ADMIN).
 */
public class EstadoEquiposView extends JFrame {

    // ── Colores por estado ─────────────────────────────────
    private static final Color COLOR_OPERATIVO        = new Color(0, 160, 80);
    private static final Color COLOR_EN_REPARACION    = new Color(210, 140, 0);
    private static final Color COLOR_FUERA_SERVICIO   = new Color(190, 40, 40);
    private static final Color COLOR_FONDO            = new Color(25, 25, 35);
    private static final Color COLOR_PANEL            = new Color(35, 35, 50);

    private final MaquinariaController maquinariaController;
    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JComboBox<String> comboFiltro;

    // Etiquetas de conteo en las tarjetas
    private JLabel lblCntOperativo;
    private JLabel lblCntReparacion;
    private JLabel lblCntFuera;

    public EstadoEquiposView(Usuario usuarioActual) {
        maquinariaController = new MaquinariaController();
        initComponents(usuarioActual);
        refrescarVista("TODOS");
    }

    // ══════════════════════════════════════════════════════
    //  CONSTRUCCIÓN DE LA UI
    // ══════════════════════════════════════════════════════
    private void initComponents(Usuario usuarioActual) {
        setTitle("Estado de Equipos — " + usuarioActual.getNombre());
        setSize(860, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(COLOR_FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ── 1. Título ──────────────────────────────────────
        JLabel lblTitulo = new JLabel("📡  Estado Actual de los Equipos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 200, 255));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── 2. Panel central (tarjetas + tabla) ───────────
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(COLOR_FONDO);

        panelCentro.add(crearPanelTarjetas(), BorderLayout.NORTH);
        panelCentro.add(crearPanelTabla(),    BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ── 3. Botón volver ────────────────────────────────
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(COLOR_FONDO);
        JButton btnVolver = new JButton("⬅ Volver");
        estilizarBoton(btnVolver, new Color(70, 70, 90));
        btnVolver.addActionListener(e -> dispose());
        panelSur.add(btnVolver);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // ── Tarjetas resumen ───────────────────────────────────
    private JPanel crearPanelTarjetas() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setBackground(COLOR_FONDO);

        // Tarjeta Operativo
        lblCntOperativo  = new JLabel("0", SwingConstants.CENTER);
        // Tarjeta En Reparación
        lblCntReparacion = new JLabel("0", SwingConstants.CENTER);
        // Tarjeta Fuera de Servicio
        lblCntFuera      = new JLabel("0", SwingConstants.CENTER);

        panel.add(crearTarjeta("✅  Operativo",         lblCntOperativo,  COLOR_OPERATIVO,      "OPERATIVO"));
        panel.add(crearTarjeta("🔧  En Reparación",     lblCntReparacion, COLOR_EN_REPARACION,  "EN_REPARACION"));
        panel.add(crearTarjeta("🚫  Fuera de Servicio", lblCntFuera,      COLOR_FUERA_SERVICIO, "FUERA_DE_SERVICIO"));
        return panel;
    }

    private JPanel crearTarjeta(String titulo, JLabel lblConteo, Color color, String filtro) {
        JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
        tarjeta.setBackground(COLOR_PANEL);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2, true),
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        ));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitulo.setForeground(color);

        lblConteo.setFont(new Font("Arial", Font.BOLD, 36));
        lblConteo.setForeground(Color.WHITE);

        // Click en la tarjeta filtra la tabla
        JButton btnFiltrar = new JButton("Ver equipos");
        estilizarBoton(btnFiltrar, color);
        btnFiltrar.addActionListener(e -> {
            comboFiltro.setSelectedItem(filtro);
            refrescarVista(filtro);
        });

        tarjeta.add(lblTitulo,  BorderLayout.NORTH);
        tarjeta.add(lblConteo,  BorderLayout.CENTER);
        tarjeta.add(btnFiltrar, BorderLayout.SOUTH);
        return tarjeta;
    }

    // ── Tabla filtrable ────────────────────────────────────
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(COLOR_FONDO);

        // Barra de filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        panelFiltro.setBackground(COLOR_FONDO);

        JLabel lblFiltro = new JLabel("Filtrar por estado:");
        lblFiltro.setForeground(Color.LIGHT_GRAY);
        lblFiltro.setFont(new Font("Arial", Font.PLAIN, 13));

        comboFiltro = new JComboBox<>(new String[]{
                "TODOS", "OPERATIVO", "EN_REPARACION", "FUERA_DE_SERVICIO"
        });
        comboFiltro.setBackground(COLOR_PANEL);
        comboFiltro.setForeground(Color.WHITE);
        comboFiltro.setFont(new Font("Arial", Font.PLAIN, 13));
        comboFiltro.addActionListener(e ->
                refrescarVista((String) comboFiltro.getSelectedItem())
        );

        JButton btnRefrescar = new JButton("🔄 Refrescar");
        estilizarBoton(btnRefrescar, new Color(0, 100, 160));
        btnRefrescar.addActionListener(e -> refrescarVista((String) comboFiltro.getSelectedItem()));

        panelFiltro.add(lblFiltro);
        panelFiltro.add(comboFiltro);
        panelFiltro.add(btnRefrescar);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Descripción", "Fecha Registro", "Estado"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.setBackground(new Color(40, 40, 55));
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(new Color(55, 55, 75));
        tabla.getTableHeader().setBackground(new Color(0, 100, 160));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setSelectionBackground(new Color(0, 160, 200));
        tabla.setSelectionForeground(Color.WHITE);

        // Renderer de colores para la columna Estado
        tabla.getColumnModel().getColumn(4).setCellRenderer(new EstadoCellRenderer());

        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(230);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(150);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(40, 40, 55));

        panel.add(panelFiltro, BorderLayout.NORTH);
        panel.add(scroll,      BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════
    //  LÓGICA DE DATOS
    // ══════════════════════════════════════════════════════
    private void refrescarVista(String filtro) {
        // Actualizar conteos en tarjetas
        int cntOp  = maquinariaController.listarPorEstado("OPERATIVO").size();
        int cntRep = maquinariaController.listarPorEstado("EN_REPARACION").size();
        int cntFue = maquinariaController.listarPorEstado("FUERA_DE_SERVICIO").size();
        lblCntOperativo .setText(String.valueOf(cntOp));
        lblCntReparacion.setText(String.valueOf(cntRep));
        lblCntFuera     .setText(String.valueOf(cntFue));

        // Cargar filas en la tabla
        modeloTabla.setRowCount(0);
        List<Maquinaria> lista = filtro.equals("TODOS")
                ? maquinariaController.listarTodas()
                : maquinariaController.listarPorEstado(filtro);

        for (Maquinaria m : lista) {
            modeloTabla.addRow(new Object[]{
                    m.getIdMaquinaria(),
                    m.getTipo(),
                    m.getDescripcion(),
                    m.getFecha(),
                    m.getEstado()
            });
        }
    }

    // ══════════════════════════════════════════════════════
    //  RENDERER CON COLORES POR ESTADO
    // ══════════════════════════════════════════════════════
    private static class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 12));
            setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

            if (!isSelected) {
                String estado = value != null ? value.toString() : "";
                switch (estado) {
                    case "OPERATIVO"         -> { setBackground(new Color(0, 100, 50));  setForeground(new Color(150, 255, 150)); setText("✅ Operativo"); }
                    case "EN_REPARACION"     -> { setBackground(new Color(120, 80, 0));  setForeground(new Color(255, 220, 100)); setText("🔧 En Reparación"); }
                    case "FUERA_DE_SERVICIO" -> { setBackground(new Color(100, 20, 20)); setForeground(new Color(255, 150, 150)); setText("🚫 Fuera de Servicio"); }
                    default                  -> { setBackground(new Color(40, 40, 55));  setForeground(Color.LIGHT_GRAY); }
                }
            }
            return this;
        }
    }

    // ── Utilidad ───────────────────────────────────────────
    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }
}
