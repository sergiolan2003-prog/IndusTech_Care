package com.industech.legacy.view;

import com.industech.controllers.MantenimientoController;
import com.industech.controllers.MaquinariaController;
import com.industech.models.Mantenimiento;
import com.industech.models.Maquinaria;
import com.industech.models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Vista: Cálculo y visualización de costos de mantenimiento.
 * Roles: ADMIN, JEFE_MANTENIMIENTO.
 * Muestra costo de mano de obra, repuestos y total por mantenimiento,
 * resumen por máquina y resumen por tipo.
 */
public class CostosMantenimientoView extends JDialog {

    private final MantenimientoController mantenimientoController;
    private final MaquinariaController    maquinariaController;
    private final NumberFormat            fmt =
            NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    // Colores
    private static final Color FONDO   = new Color(25, 25, 35);
    private static final Color PANEL   = new Color(35, 35, 50);
    private static final Color VERDE   = new Color(0, 150, 80);
    private static final Color AZUL    = new Color(0, 110, 190);
    private static final Color DORADO  = new Color(180, 130, 0);

    // Tablas
    private DefaultTableModel modeloDetalle;
    private DefaultTableModel modeloMaquina;
    private DefaultTableModel modeloTipo;

    // Totales
    private JLabel lblTotalGeneral;
    private JLabel lblTotalMano;
    private JLabel lblTotalRepuestos;

    public CostosMantenimientoView(MantenimientoController mantenimientoController,
                                   MaquinariaController maquinariaController,
                                   Usuario usuarioActual) {
        this.mantenimientoController = mantenimientoController;
        this.maquinariaController    = maquinariaController;
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Costos de Mantenimiento");
        setModal(true);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ── Título ──────────────────────────────────────────
        JLabel lblTitulo = new JLabel("💰  Costos de Mantenimiento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 220, 120));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── Panel de tarjetas de totales ────────────────────
        panelPrincipal.add(crearPanelTotales(), BorderLayout.NORTH);

        // ── Tabs con las tres vistas ─────────────────────────
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(PANEL);
        tabs.setForeground(Color.WHITE);
        tabs.setFont(new Font("Arial", Font.BOLD, 12));

        tabs.addTab("📋 Detalle por mantenimiento", crearTabDetalle());
        tabs.addTab("🔧 Resumen por máquina",       crearTabPorMaquina());
        tabs.addTab("📊 Resumen por tipo",           crearTabPorTipo());

        // Panel central = título + totales + tabs
        JPanel panelCentro = new JPanel(new BorderLayout(8, 8));
        panelCentro.setBackground(FONDO);
        panelCentro.add(lblTitulo,           BorderLayout.NORTH);
        panelCentro.add(crearPanelTotales(), BorderLayout.CENTER);

        JPanel panelAbajo = new JPanel(new BorderLayout());
        panelAbajo.setBackground(FONDO);
        panelAbajo.add(tabs, BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.NORTH);
        panelPrincipal.add(panelAbajo,  BorderLayout.CENTER);

        // ── Botones inferiores ───────────────────────────────
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelSur.setBackground(FONDO);

        JButton btnActualizar = new JButton("🔄 Actualizar costos de orden");
        JButton btnCerrar     = new JButton("✔ Cerrar");
        estilizarBoton(btnActualizar, AZUL);
        estilizarBoton(btnCerrar,     new Color(60, 60, 80));

        btnActualizar.addActionListener(e -> abrirEditorCosto());
        btnCerrar.addActionListener(e -> dispose());

        panelSur.add(btnActualizar);
        panelSur.add(btnCerrar);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // ── Tarjetas de totales ──────────────────────────────────
    private JPanel crearPanelTotales() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 12, 0));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        lblTotalGeneral   = new JLabel("$0", SwingConstants.CENTER);
        lblTotalMano      = new JLabel("$0", SwingConstants.CENTER);
        lblTotalRepuestos = new JLabel("$0", SwingConstants.CENTER);

        panel.add(crearTarjeta("💰 Costo total",      lblTotalGeneral,   new Color(0, 150, 80)));
        panel.add(crearTarjeta("👷 Mano de obra",     lblTotalMano,      new Color(0, 110, 190)));
        panel.add(crearTarjeta("🔩 Repuestos",        lblTotalRepuestos, new Color(160, 100, 0)));
        return panel;
    }

    private JPanel crearTarjeta(String titulo, JLabel lblValor, Color color) {
        JPanel t = new JPanel(new BorderLayout(4, 4));
        t.setBackground(PANEL);
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel lblT = new JLabel(titulo, SwingConstants.CENTER);
        lblT.setFont(new Font("Arial", Font.BOLD, 12));
        lblT.setForeground(color);

        lblValor.setFont(new Font("Arial", Font.BOLD, 20));
        lblValor.setForeground(Color.WHITE);

        t.add(lblT,     BorderLayout.NORTH);
        t.add(lblValor, BorderLayout.CENTER);
        return t;
    }

    // ── Tab 1: Detalle por mantenimiento ─────────────────────
    private JPanel crearTabDetalle() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        modeloDetalle = new DefaultTableModel(
                new String[]{"ID", "Tipo", "Descripción", "Máquina ID", "Mano de obra", "Repuestos", "Total", "Estado"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = crearTabla(modeloDetalle);

        // Columna "Total" en verde
        tabla.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                                                           boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setHorizontalAlignment(SwingConstants.RIGHT);
                if (!sel) {
                    setBackground(new Color(0, 60, 30));
                    setForeground(new Color(100, 255, 160));
                    setFont(getFont().deriveFont(Font.BOLD));
                }
                return this;
            }
        });

        // Columnas monetarias alineadas a la derecha
        DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
        rightAlign.setHorizontalAlignment(SwingConstants.RIGHT);
        tabla.getColumnModel().getColumn(4).setCellRenderer(rightAlign);
        tabla.getColumnModel().getColumn(5).setCellRenderer(rightAlign);

        // Anchos
        tabla.getColumnModel().getColumn(0).setPreferredWidth(35);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(110);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(90);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ── Tab 2: Resumen por máquina ───────────────────────────
    private JPanel crearTabPorMaquina() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        modeloMaquina = new DefaultTableModel(
                new String[]{"ID Máquina", "Nombre / Tipo", "Costo total"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = crearTabla(modeloMaquina);
        tabla.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                                                           boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setHorizontalAlignment(SwingConstants.RIGHT);
                if (!sel) {
                    setFont(getFont().deriveFont(Font.BOLD));
                    setForeground(new Color(100, 220, 255));
                }
                return this;
            }
        });

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ── Tab 3: Resumen por tipo ──────────────────────────────
    private JPanel crearTabPorTipo() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        modeloTipo = new DefaultTableModel(
                new String[]{"Tipo de mantenimiento", "Costo total", "% del total"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tabla = crearTabla(modeloTipo);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════
    //  CARGA DE DATOS
    // ══════════════════════════════════════════════════════════
    private void cargarDatos() {
        List<Mantenimiento> lista = mantenimientoController.listarTodos();
        double totalGeneral   = 0;
        double totalMano      = 0;
        double totalRepuestos = 0;

        // ── Tab 1: Detalle ───────────────────────────────────
        modeloDetalle.setRowCount(0);
        for (Mantenimiento m : lista) {
            totalGeneral   += m.getCostoTotal();
            totalMano      += m.getCostoManoObra();
            totalRepuestos += m.getCostoRepuestos();

            // Nombre de la máquina (si existe)
            String nombreMaq = String.valueOf(m.getIdMaquinaria());
            Maquinaria maq = maquinariaController.buscarPorId(m.getIdMaquinaria());
            if (maq != null) nombreMaq = m.getIdMaquinaria() + " - " + maq.getTipo();

            modeloDetalle.addRow(new Object[]{
                    m.getIdMantenimiento(),
                    m.getTipo(),
                    m.getDescripcion(),
                    nombreMaq,
                    fmt.format(m.getCostoManoObra()),
                    fmt.format(m.getCostoRepuestos()),
                    fmt.format(m.getCostoTotal()),
                    m.getEstado()
            });
        }

        // ── Tarjetas de totales ───────────────────────────────
        lblTotalGeneral  .setText(fmt.format(totalGeneral));
        lblTotalMano     .setText(fmt.format(totalMano));
        lblTotalRepuestos.setText(fmt.format(totalRepuestos));

        // ── Tab 2: Por máquina ────────────────────────────────
        modeloMaquina.setRowCount(0);
        Map<Integer, Double> porMaquina = mantenimientoController.getCostosPorMaquina();
        for (Map.Entry<Integer, Double> e : porMaquina.entrySet()) {
            String nombre = "Máquina " + e.getKey();
            Maquinaria maq = maquinariaController.buscarPorId(e.getKey());
            if (maq != null) nombre = e.getKey() + " - " + maq.getTipo();
            modeloMaquina.addRow(new Object[]{e.getKey(), nombre, fmt.format(e.getValue())});
        }

        // ── Tab 3: Por tipo ───────────────────────────────────
        modeloTipo.setRowCount(0);
        Map<String, Double> porTipo = mantenimientoController.getCostosPorTipo();
        final double gt = totalGeneral;
        porTipo.forEach((tipo, costo) -> {
            String porcentaje = gt > 0
                    ? String.format("%.1f%%", (costo / gt) * 100)
                    : "0%";
            modeloTipo.addRow(new Object[]{tipo, fmt.format(costo), porcentaje});
        });
    }

    // ── Editor rápido de costos ──────────────────────────────
    private void abrirEditorCosto() {
        // Pedir ID
        String idStr = JOptionPane.showInputDialog(this,
                "ID de la orden de mantenimiento a actualizar:", "Actualizar costos",
                JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.isBlank()) return;

        int id;
        try { id = Integer.parseInt(idStr.trim()); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Mantenimiento m = mantenimientoController.buscarPorId(id);
        if (m == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la orden #" + id, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Formulario de edición
        JTextField txtMano      = new JTextField(String.valueOf(m.getCostoManoObra()), 12);
        JTextField txtRepuestos = new JTextField(String.valueOf(m.getCostoRepuestos()), 12);

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Orden #" + id + " — " + m.getTipo()));
        form.add(new JLabel(""));
        form.add(new JLabel("Costo mano de obra ($):"));
        form.add(txtMano);
        form.add(new JLabel("Costo repuestos ($):"));
        form.add(txtRepuestos);

        int res = JOptionPane.showConfirmDialog(this, form,
                "Editar costos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;

        try {
            double mano      = Double.parseDouble(txtMano.getText().trim());
            double repuestos = Double.parseDouble(txtRepuestos.getText().trim());

            if (mano < 0 || repuestos < 0) throw new NumberFormatException();

            mantenimientoController.actualizarCostos(id, mano, repuestos);
            cargarDatos(); // refrescar vista
            JOptionPane.showMessageDialog(this, "Costos actualizados correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los costos deben ser números positivos.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ══════════════════════════════════════════════════════════
    //  UTILIDADES
    // ══════════════════════════════════════════════════════════
    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setBackground(new Color(40, 40, 58));
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(new Color(55, 55, 75));
        tabla.getTableHeader().setBackground(new Color(0, 80, 140));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setSelectionBackground(new Color(0, 150, 200));
        tabla.setSelectionForeground(Color.WHITE);
        return tabla;
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }
}