package com.industech.legacy.view;

import com.industech.controllers.MantenimientoController;
import com.industech.models.Mantenimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Vista: Asignación de Técnicos a Orden de Mantenimiento
 * Rol: ADMIN
 * Permite asignar uno o más técnicos a una orden seleccionada,
 * ver los ya asignados y desasignarlos.
 */
public class AsignacionTecnicoView extends JDialog {

    private final Mantenimiento             orden;
    private final MantenimientoController   mantenimientoController;
    private final TecnicoController         tecnicoController;

    private DefaultTableModel modeloDisponibles;
    private DefaultTableModel modeloAsignados;
    private JTable            tablaDisponibles;
    private JTable            tablaAsignados;
    private JLabel            lblOrdenInfo;

    // Colores
    private static final Color FONDO     = new Color(25, 25, 35);
    private static final Color PANEL     = new Color(35, 35, 50);
    private static final Color AZUL      = new Color(0, 120, 200);
    private static final Color VERDE     = new Color(0, 150, 80);
    private static final Color ROJO      = new Color(180, 50, 50);
    private static final Color MORADO    = new Color(100, 0, 180);

    public AsignacionTecnicoView(Mantenimiento orden,
                                 MantenimientoController mantenimientoController,
                                 TecnicoController tecnicoController,
                                 JFrame parent) {
        super(parent, "Asignación de Técnicos", true);
        this.orden                  = orden;
        this.mantenimientoController = mantenimientoController;
        this.tecnicoController       = tecnicoController;
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setSize(820, 520);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(FONDO);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(FONDO);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ── Título ─────────────────────────────────────────
        JLabel lblTitulo = new JLabel("👷  Asignación de Técnicos a Orden de Mantenimiento",
                SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 17));
        lblTitulo.setForeground(new Color(0, 200, 255));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // ── Info de la orden ───────────────────────────────
        String infoOrden = orden != null
                ? "Orden #" + orden.getIdMantenimiento()
                + "  |  Tipo: " + orden.getTipo()
                + "  |  Estado: " + orden.getEstado()
                + "  |  Desc: " + orden.getDescripcion()
                : "Sin orden seleccionada";
        lblOrdenInfo = new JLabel(infoOrden, SwingConstants.CENTER);
        lblOrdenInfo.setFont(new Font("Arial", Font.PLAIN, 13));
        lblOrdenInfo.setForeground(new Color(200, 200, 100));
        lblOrdenInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MORADO, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        JPanel panelNorte = new JPanel(new BorderLayout(5, 5));
        panelNorte.setBackground(FONDO);
        panelNorte.add(lblTitulo,   BorderLayout.NORTH);
        panelNorte.add(lblOrdenInfo, BorderLayout.CENTER);
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);

        // ── Panel central: dos tablas ──────────────────────
        JPanel panelCentro = new JPanel(new GridLayout(1, 3, 10, 0));
        panelCentro.setBackground(FONDO);

        panelCentro.add(crearPanelTablaDisponibles());
        panelCentro.add(crearPanelBotonesCentro());
        panelCentro.add(crearPanelTablaAsignados());

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // ── Botón cerrar ───────────────────────────────────
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(FONDO);
        JButton btnCerrar = new JButton("✔ Cerrar");
        estilizarBoton(btnCerrar, new Color(60, 60, 80));
        btnCerrar.addActionListener(e -> dispose());
        panelSur.add(btnCerrar);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // ── Tabla izquierda: técnicos disponibles ──────────────
    private JPanel crearPanelTablaDisponibles() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL, 2, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lbl = new JLabel("Técnicos disponibles", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(new Color(100, 180, 255));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        modeloDisponibles = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Especialidad"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaDisponibles = crearTablaEstilizada(modeloDisponibles);

        panel.add(lbl,                          BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaDisponibles), BorderLayout.CENTER);
        return panel;
    }

    // ── Columna central: botones de acción ─────────────────
    private JPanel crearPanelBotonesCentro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(FONDO);
        panel.setPreferredSize(new Dimension(130, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);

        JButton btnAsignar    = new JButton("Asignar ▶");
        JButton btnDesasignar = new JButton("◀ Quitar");
        JButton btnAsignarTodos = new JButton("Asignar todos ▶▶");
        JButton btnQuitarTodos  = new JButton("◀◀ Quitar todos");

        estilizarBoton(btnAsignar,      VERDE);
        estilizarBoton(btnDesasignar,   ROJO);
        estilizarBoton(btnAsignarTodos, new Color(0, 100, 60));
        estilizarBoton(btnQuitarTodos,  new Color(120, 30, 30));

        btnAsignar.addActionListener(e -> asignarSeleccionados());
        btnDesasignar.addActionListener(e -> desasignarSeleccionados());
        btnAsignarTodos.addActionListener(e -> asignarTodos());
        btnQuitarTodos.addActionListener(e -> quitarTodos());

        gbc.gridy = 0; panel.add(btnAsignar,      gbc);
        gbc.gridy = 1; panel.add(btnDesasignar,   gbc);
        gbc.gridy = 2; panel.add(new JSeparator(), gbc);
        gbc.gridy = 3; panel.add(btnAsignarTodos, gbc);
        gbc.gridy = 4; panel.add(btnQuitarTodos,  gbc);

        return panel;
    }

    // ── Tabla derecha: técnicos asignados ──────────────────
    private JPanel crearPanelTablaAsignados() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE, 2, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lbl = new JLabel("Técnicos asignados", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setForeground(new Color(100, 220, 140));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        modeloAsignados = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Especialidad"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaAsignados = crearTablaEstilizada(modeloAsignados);

        panel.add(lbl,                         BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaAsignados), BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════
    //  LÓGICA DE DATOS
    // ══════════════════════════════════════════════════════
    private void cargarDatos() {
        modeloDisponibles.setRowCount(0);
        modeloAsignados.setRowCount(0);

        if (orden == null) return;

        List<Tecnico> todos     = tecnicoController.listarTodos();
        List<Tecnico> asignados = mantenimientoController.getTecnicosDeOrden(orden.getIdMantenimiento());

        // Disponibles = todos los técnicos que NO estén ya asignados
        for (Tecnico t : todos) {
            boolean yaAsignado = false;
            for (Tecnico a : asignados) {
                if (a.getIdTecnico() == t.getIdTecnico()) { yaAsignado = true; break; }
            }
            if (!yaAsignado)
                modeloDisponibles.addRow(new Object[]{t.getIdTecnico(), t.getNombre(), t.getEspecialidad()});
        }

        // Asignados
        for (Tecnico t : asignados)
            modeloAsignados.addRow(new Object[]{t.getIdTecnico(), t.getNombre(), t.getEspecialidad()});
    }

    private void asignarSeleccionados() {
        int[] filas = tablaDisponibles.getSelectedRows();
        if (filas.length == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona al menos un técnico de la lista izquierda.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int fila : filas) {
            int idTecnico = (int) modeloDisponibles.getValueAt(fila, 0);
            Tecnico t = tecnicoController.buscarPorId(idTecnico);
            if (t != null) mantenimientoController.asignarTecnico(orden.getIdMantenimiento(), t);
        }
        cargarDatos();
        mostrarExito("Técnico(s) asignados correctamente.");
    }

    private void desasignarSeleccionados() {
        int[] filas = tablaAsignados.getSelectedRows();
        if (filas.length == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona al menos un técnico de la lista derecha.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = filas.length - 1; i >= 0; i--) {
            int idTecnico = (int) modeloAsignados.getValueAt(filas[i], 0);
            mantenimientoController.desasignarTecnico(orden.getIdMantenimiento(), idTecnico);
        }
        cargarDatos();
        mostrarExito("Técnico(s) removidos de la orden.");
    }

    private void asignarTodos() {
        List<Tecnico> todos = tecnicoController.listarTodos();
        for (Tecnico t : todos)
            mantenimientoController.asignarTecnico(orden.getIdMantenimiento(), t);
        cargarDatos();
        mostrarExito("Todos los técnicos fueron asignados.");
    }

    private void quitarTodos() {
        List<Tecnico> asignados = mantenimientoController.getTecnicosDeOrden(orden.getIdMantenimiento());
        for (Tecnico t : asignados)
            mantenimientoController.desasignarTecnico(orden.getIdMantenimiento(), t.getIdTecnico());
        cargarDatos();
        mostrarExito("Se removieron todos los técnicos de la orden.");
    }

    // ══════════════════════════════════════════════════════
    //  UTILIDADES
    // ══════════════════════════════════════════════════════
    private JTable crearTablaEstilizada(DefaultTableModel modelo) {
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
        tabla.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return tabla;
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.BOLD, 11));
        btn.setPreferredSize(new Dimension(120, 32));
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}