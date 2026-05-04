package com.industech.legacy.view;

import com.industech.controllers.MantenimientoController;
import com.industech.controllers.RepuestoController;
import com.industech.models.Mantenimiento;
import com.industech.models.Repuesto;
import com.industech.models.RepuestoOrden;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Vista: Enlace de repuestos a una orden de mantenimiento.
 * Roles: ADMIN, JEFE_MANTENIMIENTO, TECNICO.
 */
public class RepuestosOrdenView extends JDialog {

    private final Mantenimiento           orden;
    private final MantenimientoController mantenimientoController;
    private final RepuestoController      repuestoController;
    private final NumberFormat            fmt =
            NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    private static final Color FONDO  = new Color(25, 25, 35);
    private static final Color PANEL  = new Color(35, 35, 50);
    private static final Color NARANJA = new Color(160, 80, 0);
    private static final Color VERDE  = new Color(0, 140, 70);
    private static final Color ROJO   = new Color(170, 40, 40);

    private DefaultTableModel modeloInventario;
    private DefaultTableModel modeloEnlazados;
    private JLabel            lblCostoRepuestos;
    private JTextField        txtCantidad;

    public RepuestosOrdenView(Mantenimiento orden,
                              MantenimientoController mantenimientoController,
                              RepuestoController repuestoController,
                              JFrame parent) {
        super(parent, "Repuestos de la Orden #" + orden.getIdMantenimiento(), true);
        this.orden                  = orden;
        this.mantenimientoController = mantenimientoController;
        this.repuestoController      = repuestoController;
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setSize(900, 560);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBackground(FONDO);
        principal.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        // ── Título e info de la orden ────────────────────────
        JPanel panelNorte = new JPanel(new BorderLayout(6, 6));
        panelNorte.setBackground(FONDO);

        JLabel lblTitulo = new JLabel(
                "🔩  Repuestos — Orden #" + orden.getIdMantenimiento()
                        + "  |  " + orden.getTipo() + "  |  " + orden.getEstado(),
                SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(new Color(255, 180, 60));

        lblCostoRepuestos = new JLabel("Costo repuestos: " + fmt.format(orden.getCostoRepuestos()),
                SwingConstants.CENTER);
        lblCostoRepuestos.setFont(new Font("Arial", Font.BOLD, 13));
        lblCostoRepuestos.setForeground(new Color(100, 220, 140));

        panelNorte.add(lblTitulo,         BorderLayout.NORTH);
        panelNorte.add(lblCostoRepuestos, BorderLayout.SOUTH);
        principal.add(panelNorte, BorderLayout.NORTH);

        // ── Panel central: inventario | botones | enlazados ──
        JPanel panelCentro = new JPanel(new GridLayout(1, 3, 10, 0));
        panelCentro.setBackground(FONDO);
        panelCentro.add(crearPanelInventario());
        panelCentro.add(crearPanelAcciones());
        panelCentro.add(crearPanelEnlazados());
        principal.add(panelCentro, BorderLayout.CENTER);

        // ── Botón cerrar ─────────────────────────────────────
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(FONDO);
        JButton btnCerrar = new JButton("✔ Cerrar");
        estilizarBoton(btnCerrar, new Color(60, 60, 80));
        btnCerrar.addActionListener(e -> dispose());
        panelSur.add(btnCerrar);
        principal.add(panelSur, BorderLayout.SOUTH);

        add(principal);
    }

    // ── Tabla izquierda: inventario de repuestos ─────────────
    private JPanel crearPanelInventario() {
        JPanel panel = bordeado("Inventario de repuestos", new Color(0, 100, 180));

        modeloInventario = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Ref.", "Precio unit.", "Stock"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = crearTabla(modeloInventario);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(40);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ── Columna central: cantidad + botones ──────────────────
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(FONDO);
        panel.setPreferredSize(new Dimension(140, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 4, 5, 4);

        JLabel lblCant = new JLabel("Cantidad:", SwingConstants.CENTER);
        lblCant.setForeground(Color.WHITE);
        lblCant.setFont(new Font("Arial", Font.BOLD, 12));

        txtCantidad = new JTextField("1", 6);
        txtCantidad.setHorizontalAlignment(JTextField.CENTER);

        JButton btnEnlazar = new JButton("Enlazar ▶");
        JButton btnQuitar  = new JButton("◀ Quitar");

        estilizarBoton(btnEnlazar, VERDE);
        estilizarBoton(btnQuitar,  ROJO);

        btnEnlazar.addActionListener(e -> enlazarRepuesto());
        btnQuitar.addActionListener(e  -> quitarRepuesto());

        gbc.gridy = 0; panel.add(lblCant,     gbc);
        gbc.gridy = 1; panel.add(txtCantidad, gbc);
        gbc.gridy = 2; panel.add(new JSeparator(), gbc);
        gbc.gridy = 3; panel.add(btnEnlazar,  gbc);
        gbc.gridy = 4; panel.add(btnQuitar,   gbc);

        return panel;
    }

    // ── Tabla derecha: repuestos enlazados a la orden ────────
    private JPanel crearPanelEnlazados() {
        JPanel panel = bordeado("Repuestos en esta orden", NARANJA);

        modeloEnlazados = new DefaultTableModel(
                new String[]{"Nombre", "Cant.", "Precio unit.", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = crearTabla(modeloEnlazados);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(140);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(90);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════
    //  LÓGICA
    // ══════════════════════════════════════════════════════════
    private void cargarDatos() {
        // Inventario
        modeloInventario.setRowCount(0);
        for (Repuesto r : repuestoController.listarTodos())
            modeloInventario.addRow(new Object[]{
                    r.getIdRepuesto(), r.getNombre(), r.getReferencia(),
                    fmt.format(r.getPrecioUnitario()), r.getStockDisponible()
            });

        // Enlazados
        modeloEnlazados.setRowCount(0);
        for (RepuestoOrden ro : mantenimientoController.getRepuestosDeOrden(orden.getIdMantenimiento()))
            modeloEnlazados.addRow(new Object[]{
                    ro.getRepuesto().getNombre(),
                    ro.getCantidad(),
                    fmt.format(ro.getCostoUnitarioAlMomento()),
                    fmt.format(ro.getSubtotal())
            });

        // Actualizar etiqueta de costo
        lblCostoRepuestos.setText("Costo repuestos: " + fmt.format(orden.getCostoRepuestos()));
    }

    private void enlazarRepuesto() {
        int fila = getTablaInventario().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un repuesto del inventario.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idRepuesto = (int) modeloInventario.getValueAt(fila, 0);
        Repuesto rep = repuestoController.buscarPorId(idRepuesto);
        if (rep == null) return;

        boolean ok = mantenimientoController.enlazarRepuesto(orden.getIdMantenimiento(), rep, cantidad);
        if (ok) {
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Disponible: " + rep.getStockDisponible() + " " + rep.getUnidad(),
                    "Sin stock", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void quitarRepuesto() {
        int fila = getTablaEnlazados().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un repuesto de la lista derecha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<RepuestoOrden> lista = mantenimientoController.getRepuestosDeOrden(orden.getIdMantenimiento());
        if (fila >= lista.size()) return;

        RepuestoOrden ro = lista.get(fila);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Quitar " + ro.getRepuesto().getNombre() + " de la orden?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            mantenimientoController.quitarRepuesto(orden.getIdMantenimiento(), ro);
            cargarDatos();
        }
    }

    // ── Referencias a tablas para los métodos de acción ──────
    private JTable tablaInventario;
    private JTable tablaEnlazados;

    private JTable getTablaInventario() { return tablaInventario; }
    private JTable getTablaEnlazados()  { return tablaEnlazados; }

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
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setSelectionBackground(new Color(0, 150, 200));
        tabla.setSelectionForeground(Color.WHITE);

        // Guardar referencia según el modelo
        if (modelo == modeloInventario) tablaInventario = tabla;
        else if (modelo == modeloEnlazados) tablaEnlazados = tabla;

        return tabla;
    }

    private JPanel bordeado(String titulo, Color color) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(color.brighter());
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        panel.add(lbl, BorderLayout.NORTH);
        return panel;
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        btn.setPreferredSize(new Dimension(120, 30));
    }
}