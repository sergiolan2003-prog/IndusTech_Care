package com.industech.legacy.view;

import com.industech.controllers.*;
import com.industech.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestionView extends JFrame {

    private String  entidad;
    private Usuario usuarioActual;

    private JTable             tabla;
    private DefaultTableModel  modeloTabla;
    private JTextField         txtBuscar;
    private JComboBox<String>  comboEstado;
    private JComboBox<String>  comboTipo;

    // Controllers
    private MaquinariaController    maquinariaController    = new MaquinariaController();
    private TecnicoController       tecnicoController       = new TecnicoController();
    private FallaController         fallaController         = new FallaController();
    private MantenimientoController mantenimientoController = new MantenimientoController();
    private ReporteController       reporteController       = new ReporteController();
    private UsuarioController       usuarioController       = new UsuarioController();
    private RepuestoController      repuestoController      = new RepuestoController();

    public GestionView(String entidad, Usuario usuarioActual) {
        this.entidad       = entidad;
        this.usuarioActual = usuarioActual;
        initComponents();
        cargarDatos();
    }

    // ══════════════════════════════════════════════════════
    //  UI principal
    // ══════════════════════════════════════════════════════
    private void initComponents() {
        setTitle("Gestión de " + entidad);
        setSize(820, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(new Color(30, 30, 30));

        // Título
        JLabel lblTitulo = new JLabel("Gestión de " + entidad, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 180, 255));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Barra de filtros
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        panelBuscar.setBackground(new Color(30, 30, 30));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setForeground(Color.WHITE);
        txtBuscar = new JTextField(10);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setForeground(Color.WHITE);
        comboEstado = new JComboBox<>(obtenerOpcionesEstado());
        comboEstado.setBackground(new Color(45, 45, 45));
        comboEstado.setForeground(Color.WHITE);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(Color.WHITE);
        comboTipo = new JComboBox<>(obtenerOpcionesTipo());
        comboTipo.setBackground(new Color(45, 45, 45));
        comboTipo.setForeground(Color.WHITE);

        JButton btnFiltrar      = new JButton("🔍 Filtrar");
        JButton btnMostrarTodos = new JButton("↺ Todos");
        btnFiltrar.addActionListener(e -> aplicarFiltros());
        btnMostrarTodos.addActionListener(e -> {
            txtBuscar.setText("");
            comboEstado.setSelectedIndex(0);
            comboTipo.setSelectedIndex(0);
            cargarDatos();
        });

        panelBuscar.add(lblBuscar);
        panelBuscar.add(txtBuscar);
        if (tieneEstado()) { panelBuscar.add(lblEstado); panelBuscar.add(comboEstado); }
        if (tieneTipo())   { panelBuscar.add(lblTipo);   panelBuscar.add(comboTipo);   }
        panelBuscar.add(btnFiltrar);
        panelBuscar.add(btnMostrarTodos);

        // Tabla
        modeloTabla = new DefaultTableModel() {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        modeloTabla.setColumnIdentifiers(obtenerColumnas());
        tabla = new JTable(modeloTabla);
        tabla.setBackground(new Color(45, 45, 45));
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(new Color(60, 60, 60));
        tabla.getTableHeader().setBackground(new Color(0, 120, 180));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(0, 180, 255));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabla);
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelBuscar, BorderLayout.NORTH);
        panelCentro.add(scroll,      BorderLayout.CENTER);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBackground(new Color(30, 30, 30));

        JButton btnAgregar        = new JButton("➕ Agregar");
        JButton btnEditar         = new JButton("✏️ Editar");
        JButton btnEliminar       = new JButton("🗑 Eliminar");
        JButton btnAsignarTecnico = new JButton("👷 Asignar Técnico");
        JButton btnVolver         = new JButton("⬅ Volver");
        JButton btnEstadisticas   = new JButton("📊 Estadísticas");
        JButton btnVerEstado      = new JButton("📡 Ver Estado Equipos");
        JButton btnCostos         = new JButton("💰 Ver Costos");
        JButton btnRepuestos      = new JButton("🔩 Repuestos");

        estilizarBoton(btnAgregar,        new Color(0,   150,  80));
        estilizarBoton(btnEditar,         new Color(180, 130,   0));
        estilizarBoton(btnEliminar,       new Color(180,  50,  50));
        estilizarBoton(btnVolver,         new Color(80,   80,  80));
        estilizarBoton(btnEstadisticas,   new Color(0,   100, 160));
        estilizarBoton(btnVerEstado,      new Color(0,   140, 130));
        estilizarBoton(btnAsignarTecnico, new Color(70,    0, 160));
        estilizarBoton(btnCostos,         new Color(0,   120,  60));
        estilizarBoton(btnRepuestos,      new Color(120,  60,   0));

        btnAgregar.addActionListener(e        -> accionAgregar());
        btnEditar.addActionListener(e         -> accionEditar());
        btnEliminar.addActionListener(e       -> accionEliminar());
        btnVolver.addActionListener(e         -> this.dispose());
        btnEstadisticas.addActionListener(e   -> accionEstadisticas());
        btnVerEstado.addActionListener(e      -> new EstadoEquiposView(usuarioActual).setVisible(true));
        btnAsignarTecnico.addActionListener(e -> accionAsignarTecnico());
        btnCostos.addActionListener(e         -> accionVerCostos());
        btnRepuestos.addActionListener(e      -> accionRepuestos());

        String rol          = usuarioActual.getRol();
        boolean puedeEscribir = rol.equals("ADMIN") || rol.equals("JEFE_MANTENIMIENTO") || rol.equals("TECNICO");
        boolean soloLectura   = rol.equals("CONSULTOR");

        panelBotones.add(btnAgregar);
        if (puedeEscribir) {
            panelBotones.add(btnEditar);
            panelBotones.add(btnEliminar);
        }
        if (entidad.equals("REPORTE"))    panelBotones.add(btnEstadisticas);
        if (entidad.equals("MAQUINARIA")) panelBotones.add(btnVerEstado);
        if (entidad.equals("MANTENIMIENTO") &&
                (rol.equals("ADMIN") || rol.equals("JEFE_MANTENIMIENTO")))
            panelBotones.add(btnAsignarTecnico);
        if (entidad.equals("MANTENIMIENTO") &&
                (rol.equals("ADMIN") || rol.equals("JEFE_MANTENIMIENTO")))
            panelBotones.add(btnCostos);
        if (entidad.equals("MANTENIMIENTO"))
            panelBotones.add(btnRepuestos);
        panelBotones.add(btnVolver);

        if (soloLectura && !entidad.equals("FALLA")) {
            btnAgregar.setEnabled(false);
            btnAgregar.setToolTipText("No tienes permiso para agregar " + entidad);
        }

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    // ══════════════════════════════════════════════════════
    //  Columnas
    // ══════════════════════════════════════════════════════
    private String[] obtenerColumnas() {
        return switch (entidad) {
            case "MAQUINARIA"    -> new String[]{"ID", "Tipo", "Fecha", "Descripción", "Estado"};
            case "TECNICO"       -> new String[]{"ID", "Nombre", "Especialidad", "Teléfono"};
            case "FALLA"         -> new String[]{"ID", "Fecha", "Descripción", "Gravedad"};
            case "MANTENIMIENTO" -> new String[]{"ID", "Tipo", "Fecha", "Descripción", "Estado"};
            case "REPORTE"       -> new String[]{"ID", "Fecha", "Tipo", "Descripción", "Estado"};
            case "USUARIO"       -> new String[]{"ID", "Nombre", "Dirección", "Teléfono", "Rol", "Contraseña"};
            case "REPUESTO"      -> new String[]{"ID", "Nombre", "Referencia", "Unidad", "Precio unitario", "Stock"};
            default              -> new String[]{"ID", "Nombre"};
        };
    }

    // ══════════════════════════════════════════════════════
    //  Cargar datos
    // ══════════════════════════════════════════════════════
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        switch (entidad) {
            case "MAQUINARIA" -> {
                for (Maquinaria m : maquinariaController.listarTodas())
                    modeloTabla.addRow(new Object[]{m.getIdMaquinaria(), m.getTipo(), m.getFecha(), m.getDescripcion(), m.getEstado()});
            }
            case "TECNICO" -> {
                for (Tecnico t : tecnicoController.listarTodos())
                    modeloTabla.addRow(new Object[]{t.getIdTecnico(), t.getNombre(), t.getEspecialidad(), t.getTelefono()});
            }
            case "FALLA" -> {
                for (Falla f : fallaController.listarTodas())
                    modeloTabla.addRow(new Object[]{f.getIdFalla(), f.getFecha(), f.getDescripcion(), f.getGravedad()});
            }
            case "MANTENIMIENTO" -> {
                for (Mantenimiento m : mantenimientoController.listarTodos())
                    modeloTabla.addRow(new Object[]{m.getIdMantenimiento(), m.getTipo(), m.getFecha(), m.getDescripcion(), m.getEstado()});
            }
            case "REPORTE" -> {
                for (Reporte r : reporteController.listarTodos())
                    modeloTabla.addRow(new Object[]{r.getIdReporte(), r.getFecha(), r.getTipo(), r.getDescripcion(), r.getEstado()});
            }
            case "USUARIO" -> {
                for (Usuario u : usuarioController.listarTodos())
                    modeloTabla.addRow(new Object[]{u.getIdUsuario(), u.getNombre(), u.getDireccion(), u.getTelefono(), u.getRol(), u.getContrasena()});
            }
            case "REPUESTO" -> {
                for (Repuesto r : repuestoController.listarTodos())
                    modeloTabla.addRow(new Object[]{r.getIdRepuesto(), r.getNombre(), r.getReferencia(), r.getUnidad(), r.getPrecioUnitario(), r.getStockDisponible()});
            }
        }
    }

    // ══════════════════════════════════════════════════════
    //  CRUD — Agregar
    // ══════════════════════════════════════════════════════
    private void accionAgregar() {
        switch (entidad) {
            case "MAQUINARIA"    -> mostrarFormMaquinaria(null);
            case "TECNICO"       -> mostrarFormTecnico(null);
            case "FALLA"         -> mostrarFormFalla(null);
            case "MANTENIMIENTO" -> mostrarFormMantenimiento(null);
            case "REPORTE"       -> mostrarFormReporte(null);
            case "USUARIO"       -> mostrarFormUsuario(null);
            case "REPUESTO"      -> mostrarFormRepuesto(null);
        }
    }

    // ══════════════════════════════════════════════════════
    //  CRUD — Editar
    // ══════════════════════════════════════════════════════
    private void accionEditar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        switch (entidad) {
            case "MAQUINARIA"    -> mostrarFormMaquinaria(maquinariaController.buscarPorId(id));
            case "TECNICO"       -> mostrarFormTecnico(tecnicoController.buscarPorId(id));
            case "FALLA"         -> mostrarFormFalla(fallaController.buscarPorId(id));
            case "MANTENIMIENTO" -> mostrarFormMantenimiento(mantenimientoController.buscarPorId(id));
            case "REPORTE"       -> mostrarFormReporte(reporteController.buscarPorId(id));
            case "USUARIO"       -> mostrarFormUsuario(usuarioController.buscarPorId(id));
            case "REPUESTO"      -> mostrarFormRepuesto(repuestoController.buscarPorId(id));
        }
    }

    // ══════════════════════════════════════════════════════
    //  CRUD — Eliminar
    // ══════════════════════════════════════════════════════
    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar el registro con ID " + id + "?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = eliminarPorEntidad(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean eliminarPorEntidad(int id) {
        return switch (entidad) {
            case "MAQUINARIA"    -> maquinariaController.eliminar(id);
            case "TECNICO"       -> tecnicoController.eliminar(id);
            case "FALLA"         -> fallaController.eliminar(id);
            case "MANTENIMIENTO" -> mantenimientoController.eliminar(id);
            case "REPORTE"       -> reporteController.eliminar(id);
            case "USUARIO"       -> usuarioController.eliminarUsuario(id);
            case "REPUESTO"      -> repuestoController.eliminar(id);
            default              -> false;
        };
    }

    // ══════════════════════════════════════════════════════
    //  FORMULARIOS
    // ══════════════════════════════════════════════════════

    /** Formulario MAQUINARIA */
    private void mostrarFormMaquinaria(Maquinaria m) {
        boolean esEdicion = (m != null);
        JDialog dialog = crearDialog(esEdicion ? "Editar Maquinaria" : "Agregar Maquinaria");

        JTextField txtTipo  = new JTextField(esEdicion ? m.getTipo() : "");
        JTextField txtFecha = new JTextField(esEdicion ? fmtFecha(m.getFecha()) : hoy());
        JTextField txtDesc  = new JTextField(esEdicion ? m.getDescripcion() : "");
        JComboBox<String> cboEstado = new JComboBox<>(new String[]{"OPERATIVO", "EN_REPARACION", "FUERA_DE_SERVICIO"});
        if (esEdicion) cboEstado.setSelectedItem(m.getEstado());

        JPanel form = crearPanelForm(
                "Tipo:", txtTipo,
                "Fecha (dd/MM/yyyy):", txtFecha,
                "Descripción:", txtDesc,
                "Estado:", cboEstado
        );

        JButton btnGuardar = new JButton(esEdicion ? "💾 Guardar cambios" : "➕ Agregar");
        estilizarBoton(btnGuardar, new Color(0, 150, 80));
        btnGuardar.addActionListener(e -> {
            // Validaciones
            if (txtTipo.getText().trim().isEmpty()) { error(dialog, "El tipo no puede estar vacío."); return; }
            if (txtDesc.getText().trim().isEmpty()) { error(dialog, "La descripción no puede estar vacía."); return; }
            Date fecha = parseFecha(dialog, txtFecha.getText().trim());
            if (fecha == null) return;

            if (esEdicion) {
                m.setTipo(txtTipo.getText().trim());
                m.setFecha(fecha);
                m.setDescripcion(txtDesc.getText().trim());
                m.setEstado((String) cboEstado.getSelectedItem());
                boolean ok = maquinariaController.actualizar(m);
                mostrarResultado(dialog, ok);
            } else {
                int nuevoId = generarId();
                Maquinaria nueva = new Maquinaria(nuevoId,
                        txtTipo.getText().trim(), fecha,
                        txtDesc.getText().trim(),
                        (String) cboEstado.getSelectedItem());
                boolean ok = maquinariaController.agregar(nueva);
                mostrarResultado(dialog, ok);
            }
            cargarDatos();
        });

        mostrarDialog(dialog, form, btnGuardar);
    }

    /** Formulario TECNICO */
    private void mostrarFormTecnico(Tecnico t) {
        boolean esEdicion = (t != null);
        JDialog dialog = crearDialog(esEdicion ? "Editar Técnico" : "Agregar Técnico");

        JTextField txtNombre       = new JTextField(esEdicion ? t.getNombre()       : "");
        JTextField txtEspecialidad = new JTextField(esEdicion ? t.getEspecialidad() : "");
        JTextField txtTelefono     = new JTextField(esEdicion ? t.getTelefono()     : "");

        JPanel form = crearPanelForm(
                "Nombre:", txtNombre,
                "Especialidad:", txtEspecialidad,
                "Teléfono:", txtTelefono
        );

        JButton btnGuardar = new JButton(esEdicion ? "💾 Guardar cambios" : "➕ Agregar");
        estilizarBoton(btnGuardar, new Color(0, 150, 80));
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty())           { error(dialog, "El nombre no puede estar vacío."); return; }
            if (txtEspecialidad.getText().trim().isEmpty())     { error(dialog, "La especialidad no puede estar vacía."); return; }
            String tel = txtTelefono.getText().trim();
            if (tel.isEmpty())                                  { error(dialog, "El teléfono no puede estar vacío."); return; }
            if (!tel.matches("\\d{7,15}"))                      { error(dialog, "El teléfono debe tener entre 7 y 15 dígitos."); return; }

            if (esEdicion) {
                t.setNombre(txtNombre.getText().trim());
                t.setEspecialidad(txtEspecialidad.getText().trim());
                t.setTelefono(tel);
                boolean ok = tecnicoController.actualizar(t);
                mostrarResultado(dialog, ok);
            } else {
                Tecnico nuevo = new Tecnico(generarId(),
                        txtNombre.getText().trim(),
                        txtEspecialidad.getText().trim(), tel);
                boolean ok = tecnicoController.agregar(nuevo);
                mostrarResultado(dialog, ok);
            }
            cargarDatos();
        });

        mostrarDialog(dialog, form, btnGuardar);
    }

    /** Formulario FALLA */
    private void mostrarFormFalla(Falla f) {
        boolean esEdicion = (f != null);
        JDialog dialog = crearDialog(esEdicion ? "Editar Falla" : "Registrar Falla");

        JTextField txtFecha = new JTextField(esEdicion ? fmtFecha(f.getFecha()) : hoy());
        JTextField txtDesc  = new JTextField(esEdicion ? f.getDescripcion() : "");
        JComboBox<String> cboGravedad = new JComboBox<>(new String[]{"BAJA", "MEDIA", "ALTA"});
        if (esEdicion) cboGravedad.setSelectedItem(f.getGravedad());

        JPanel form = crearPanelForm(
                "Fecha (dd/MM/yyyy):", txtFecha,
                "Descripción:", txtDesc,
                "Gravedad:", cboGravedad
        );

        JButton btnGuardar = new JButton(esEdicion ? "💾 Guardar cambios" : "➕ Registrar");
        estilizarBoton(btnGuardar, new Color(0, 150, 80));
        btnGuardar.addActionListener(e -> {
            if (txtDesc.getText().trim().isEmpty()) { error(dialog, "La descripción no puede estar vacía."); return; }
            if (txtDesc.getText().trim().length() < 5) { error(dialog, "La descripción debe tener al menos 5 caracteres."); return; }
            Date fecha = parseFecha(dialog, txtFecha.getText().trim());
            if (fecha == null) return;

            if (esEdicion) {
                f.setFecha(fecha);
                f.setDescripcion(txtDesc.getText().trim());
                f.setGravedad((String) cboGravedad.getSelectedItem());
                boolean ok = fallaController.actualizar(f);
                mostrarResultado(dialog, ok);
            } else {
                Falla nueva = new Falla(generarId(), fecha,
                        txtDesc.getText().trim(),
                        (String) cboGravedad.getSelectedItem());
                boolean ok = fallaController.agregar(nueva);
                mostrarResultado(dialog, ok);
            }
            cargarDatos();
        });

        mostrarDialog(dialog, form, btnGuardar);
    }

    /** Formulario MANTENIMIENTO */
    private void mostrarFormMantenimiento(Mantenimiento mant) {
        boolean esEdicion = (mant != null);
        JDialog dialog = crearDialog(esEdicion ? "Editar Mantenimiento" : "Agregar Mantenimiento");

        JComboBox<String> cboTipo = new JComboBox<>(new String[]{"Preventivo", "Correctivo"});
        if (esEdicion) cboTipo.setSelectedItem(mant.getTipo());
        JTextField txtFecha = new JTextField(esEdicion ? fmtFecha(mant.getFecha()) : hoy());
        JTextField txtDesc  = new JTextField(esEdicion ? mant.getDescripcion() : "");
        JComboBox<String> cboEstado = new JComboBox<>(new String[]{"ACTIVO", "FINALIZADO"});
        if (esEdicion) cboEstado.setSelectedItem(mant.getEstado());
        JTextField txtCostoMano = new JTextField(esEdicion ? String.valueOf(mant.getCostoManoObra()) : "0");
        JTextField txtIdMaq     = new JTextField(esEdicion ? String.valueOf(mant.getIdMaquinaria())  : "");

        JPanel form = crearPanelForm(
                "Tipo:", cboTipo,
                "Fecha (dd/MM/yyyy):", txtFecha,
                "Descripción:", txtDesc,
                "Estado:", cboEstado,
                "Costo mano de obra:", txtCostoMano,
                "ID Maquinaria:", txtIdMaq
        );

        JButton btnGuardar = new JButton(esEdicion ? "💾 Guardar cambios" : "➕ Agregar");
        estilizarBoton(btnGuardar, new Color(0, 150, 80));
        btnGuardar.addActionListener(e -> {
            if (txtDesc.getText().trim().isEmpty()) { error(dialog, "La descripción no puede estar vacía."); return; }
            Date fecha = parseFecha(dialog, txtFecha.getText().trim());
            if (fecha == null) return;
            double costo;
            try { costo = Double.parseDouble(txtCostoMano.getText().trim()); if (costo < 0) throw new NumberFormatException(); }
            catch (NumberFormatException ex) { error(dialog, "El costo de mano de obra debe ser un número positivo."); return; }
            int idMaq = 0;
            if (!txtIdMaq.getText().trim().isEmpty()) {
                try { idMaq = Integer.parseInt(txtIdMaq.getText().trim()); }
                catch (NumberFormatException ex) { error(dialog, "El ID de maquinaria debe ser un número entero."); return; }
            }

            if (esEdicion) {
                mant.setTipo((String) cboTipo.getSelectedItem());
                mant.setFecha(fecha);
                mant.setDescripcion(txtDesc.getText().trim());
                mant.setEstado((String) cboEstado.getSelectedItem());
                mant.setCostoManoObra(costo);
                mant.setIdMaquinaria(idMaq);
                boolean ok = mantenimientoController.actualizar(mant);
                mostrarResultado(dialog, ok);
            } else {
                Mantenimiento nuevo = new Mantenimiento(generarId(),
                        (String) cboTipo.getSelectedItem(), fecha,
                        txtDesc.getText().trim(),
                        (String) cboEstado.getSelectedItem(),
                        costo, 0, idMaq);
                boolean ok = mantenimientoController.agregar(nuevo);
                mostrarResultado(dialog, ok);
            }
            cargarDatos();
        });

        mostrarDialog(dialog, form, btnGuardar);
    }

    /** Formulario REPORTE */
    private void mostrarFormReporte(Reporte r) {
        boolean esEdicion = (r != null);
        JDialog dialog = crearDialog(esEdicion ? "Editar Reporte" : "Crear Reporte");

        JTextField txtFecha = new JTextField(esEdicion ? fmtFecha(r.getFecha()) : hoy());
        JComboBox<String> cboTipo = new JComboBox<>(new String[]{"Falla", "Mantenimiento"});
        if (esEdicion) cboTipo.setSelectedItem(r.getTipo());
        JTextField txtDesc = new JTextField(esEdicion ? r.getDescripcion() : "");
        JComboBox<String> cboEstado = new JComboBox<>(new String[]{"ABIERTO", "CERRADO"});
        if (esEdicion) cboEstado.setSelectedItem(r.getEstado());

        JPanel form = crearPanelForm(
                "Fecha (dd/MM/yyyy):", txtFecha,
                "Tipo:", cboTipo,
                "Descripción:", txtDesc,
                "Estado:", cboEstado
        );

        JButton btnGuardar = new JButton(esEdicion ? "💾 Guardar cambios" : "➕ Crear");
        estilizarBoton(btnGuardar, new Color(0, 150, 80));
        btnGuardar.addActionListener(e -> {
            if (txtDesc.getText().trim().isEmpty()) { error(dialog, "La descripción no puede estar vacía."); return; }
            Date fecha = parseFecha(dialog, txtFecha.getText().trim());
            if (fecha == null) return;

            if (esEdicion) {
                r.setFecha(fecha);
                r.setTipo((String) cboTipo.getSelectedItem());
                r.setDescripcion(txtDesc.getText().trim());
                r.setEstado((String) cboEstado.getSelectedItem());
                boolean ok = reporteController.actualizar(r);
                mostrarResultado(dialog, ok);
            } else {
                Reporte nuevo = new Reporte(generarId(), fecha,
                        (String) cboTipo.getSelectedItem(),
                        txtDesc.getText().trim(),
                        (String) cboEstado.getSelectedItem());
                boolean ok = reporteController.agregar(nuevo);
                mostrarResultado(dialog, ok);
            }
            cargarDatos();
        });

        mostrarDialog(dialog, form, btnGuardar);
    }

    /** Formulario USUARIO */
    private void mostrarFormUsuario(Usuario u) {
        boolean esEdicion = (u != null);
        JDialog dialog = crearDialog(esEdicion ? "Editar Usuario" : "Agregar Usuario");

        JTextField     txtNombre    = new JTextField(esEdicion ? u.getNombre()    : "");
        JTextField     txtDireccion = new JTextField(esEdicion ? u.getDireccion() : "");
        JTextField     txtTelefono  = new JTextField(esEdicion ? u.getTelefono()  : "");
        JPasswordField txtContrasena = new JPasswordField(esEdicion ? "" : "");
        JLabel         lblPassHint  = new JLabel(esEdicion ? "(dejar vacío = no cambiar)" : "");
        lblPassHint.setFont(lblPassHint.getFont().deriveFont(10f));
        lblPassHint.setForeground(Color.GRAY);

        JComboBox<String> cboRol = new JComboBox<>(
                new String[]{"ADMIN", "JEFE_MANTENIMIENTO", "TECNICO", "CONSULTOR"});
        if (esEdicion) cboRol.setSelectedItem(u.getRol());

        // Panel de formulario con contraseña
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill   = GridBagConstraints.HORIZONTAL;

        String[] labels  = {"Nombre:", "Dirección:", "Teléfono:", "Contraseña:", "", "Rol:"};
        JComponent[] flds = {txtNombre, txtDireccion, txtTelefono, txtContrasena, lblPassHint, cboRol};
        for (int i = 0; i < labels.length; i++) {
            g.gridx = 0; g.gridy = i; g.weightx = 0;
            form.add(new JLabel(labels[i]), g);
            g.gridx = 1; g.weightx = 1;
            form.add(flds[i], g);
        }

        JButton btnGuardar = new JButton(esEdicion ? "💾 Guardar cambios" : "➕ Agregar");
        estilizarBoton(btnGuardar, new Color(0, 150, 80));
        btnGuardar.addActionListener(e -> {
            String nombre    = txtNombre.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String tel       = txtTelefono.getText().trim();
            String pass      = new String(txtContrasena.getPassword()).trim();
            String rol       = (String) cboRol.getSelectedItem();

            if (nombre.isEmpty())          { error(dialog, "El nombre no puede estar vacío.");   return; }
            if (direccion.isEmpty())       { error(dialog, "La dirección no puede estar vacía."); return; }
            if (tel.isEmpty())             { error(dialog, "El teléfono no puede estar vacío."); return; }
            if (!tel.matches("\\d{7,15}")) { error(dialog, "El teléfono debe tener 7–15 dígitos."); return; }
            if (!esEdicion && pass.isEmpty()) { error(dialog, "La contraseña no puede estar vacía."); return; }

            if (esEdicion) {
                u.setNombre(nombre);
                u.setDireccion(direccion);
                u.setTelefono(tel);
                u.setRol(rol);
                if (!pass.isEmpty()) u.setContrasena(pass); // solo cambiar si escribió algo
                boolean ok = usuarioController.actualizarUsuario(u);
                mostrarResultado(dialog, ok);
            } else {
                // ID en 0 → MySQL lo genera con AUTO_INCREMENT
                Usuario nuevo = new Usuario(0, nombre, direccion, tel, rol, pass);
                boolean ok = usuarioController.agregarUsuario(nuevo);
                if (ok) {
                    JOptionPane.showMessageDialog(dialog,
                            "Usuario creado exitosamente.\n" +
                            "Nombre: "      + nombre + "\n" +
                            "Contraseña: "  + pass   + "\n" +
                            "Rol: "         + rol,
                            "✅ Usuario creado", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    error(dialog, "No se pudo crear el usuario. ¿El nombre ya existe?");
                }
            }
            cargarDatos();
        });

        mostrarDialog(dialog, form, btnGuardar);
    }

    /** Formulario REPUESTO */
    private void mostrarFormRepuesto(Repuesto r) {
        boolean esEdicion = (r != null);
        JDialog dialog = crearDialog(esEdicion ? "Editar Repuesto" : "Agregar Repuesto");

        JTextField txtNombre      = new JTextField(esEdicion ? r.getNombre()      : "");
        JTextField txtReferencia  = new JTextField(esEdicion ? r.getReferencia()   : "");
        JTextField txtUnidad      = new JTextField(esEdicion ? r.getUnidad()       : "");
        JTextField txtPrecio      = new JTextField(esEdicion ? String.valueOf(r.getPrecioUnitario())  : "");
        JTextField txtStock       = new JTextField(esEdicion ? String.valueOf(r.getStockDisponible()) : "");

        JPanel form = crearPanelForm(
                "Nombre:", txtNombre,
                "Referencia:", txtReferencia,
                "Unidad:", txtUnidad,
                "Precio unitario:", txtPrecio,
                "Stock disponible:", txtStock
        );

        JButton btnGuardar = new JButton(esEdicion ? "💾 Guardar cambios" : "➕ Agregar");
        estilizarBoton(btnGuardar, new Color(0, 150, 80));
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty())     { error(dialog, "El nombre no puede estar vacío."); return; }
            if (txtReferencia.getText().trim().isEmpty()) { error(dialog, "La referencia no puede estar vacía."); return; }
            if (txtUnidad.getText().trim().isEmpty())     { error(dialog, "La unidad no puede estar vacía."); return; }
            double precio;
            int stock;
            try { precio = Double.parseDouble(txtPrecio.getText().trim()); if (precio < 0) throw new NumberFormatException(); }
            catch (NumberFormatException ex) { error(dialog, "El precio debe ser un número positivo."); return; }
            try { stock = Integer.parseInt(txtStock.getText().trim()); if (stock < 0) throw new NumberFormatException(); }
            catch (NumberFormatException ex) { error(dialog, "El stock debe ser un número entero positivo."); return; }

            if (esEdicion) {
                r.setNombre(txtNombre.getText().trim());
                r.setReferencia(txtReferencia.getText().trim());
                r.setUnidad(txtUnidad.getText().trim());
                r.setPrecioUnitario(precio);
                r.setStockDisponible(stock);
                boolean ok = repuestoController.actualizar(r);
                mostrarResultado(dialog, ok);
            } else {
                Repuesto nuevo = new Repuesto(generarId(),
                        txtNombre.getText().trim(),
                        txtReferencia.getText().trim(),
                        txtUnidad.getText().trim(),
                        precio, stock);
                boolean ok = repuestoController.agregar(nuevo);
                mostrarResultado(dialog, ok);
            }
            cargarDatos();
        });

        mostrarDialog(dialog, form, btnGuardar);
    }

    // ══════════════════════════════════════════════════════
    //  Estadísticas
    // ══════════════════════════════════════════════════════
    private void accionEstadisticas() {
        String resultado = reporteController.generarEstadisticas(
                mantenimientoController.listarTodos(),
                maquinariaController.listarTodas()
        );
        JTextArea textArea = new JTextArea(resultado);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setBackground(new Color(30, 30, 30));
        textArea.setForeground(new Color(0, 220, 120));
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(480, 320));
        JOptionPane.showMessageDialog(this, scroll,
                "Estadísticas del Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    // ══════════════════════════════════════════════════════
    //  Asignar técnico / costos / repuestos
    // ══════════════════════════════════════════════════════
    private void accionAsignarTecnico() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una orden de mantenimiento para asignar técnicos.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idMantenimiento = (int) modeloTabla.getValueAt(fila, 0);
        Mantenimiento orden = mantenimientoController.buscarPorId(idMantenimiento);
        if (orden == null) return;
        new AsignacionTecnicoView(orden, mantenimientoController, tecnicoController, this).setVisible(true);
    }

    private void accionVerCostos() {
        new CostosMantenimientoView(mantenimientoController, maquinariaController, usuarioActual).setVisible(true);
    }

    private void accionRepuestos() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una orden de mantenimiento primero.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idMantenimiento = (int) modeloTabla.getValueAt(fila, 0);
        Mantenimiento orden = mantenimientoController.buscarPorId(idMantenimiento);
        if (orden == null) return;
        new RepuestosOrdenView(orden, mantenimientoController, repuestoController, this).setVisible(true);
    }

    // ══════════════════════════════════════════════════════
    //  Filtros
    // ══════════════════════════════════════════════════════
    private void aplicarFiltros() {
        String texto  = txtBuscar.getText().trim();
        String estado = comboEstado != null ? (String) comboEstado.getSelectedItem() : "TODOS";
        String tipo   = comboTipo   != null ? (String) comboTipo.getSelectedItem()   : "TODOS";

        modeloTabla.setRowCount(0);
        switch (entidad) {
            case "MAQUINARIA" -> {
                for (Maquinaria m : maquinariaController.filtrar(estado, tipo, texto))
                    modeloTabla.addRow(new Object[]{m.getIdMaquinaria(), m.getTipo(), m.getFecha(), m.getDescripcion(), m.getEstado()});
            }
            case "MANTENIMIENTO" -> {
                for (Mantenimiento m : mantenimientoController.filtrar(estado, tipo, texto))
                    modeloTabla.addRow(new Object[]{m.getIdMantenimiento(), m.getTipo(), m.getFecha(), m.getDescripcion(), m.getEstado()});
            }
            case "REPORTE" -> {
                for (Reporte r : reporteController.filtrar(estado, tipo, texto))
                    modeloTabla.addRow(new Object[]{r.getIdReporte(), r.getFecha(), r.getTipo(), r.getDescripcion(), r.getEstado()});
            }
            case "TECNICO" -> {
                for (Tecnico t : tecnicoController.listarTodos()) {
                    if (texto.isBlank()
                            || t.getNombre().toLowerCase().contains(texto.toLowerCase())
                            || t.getEspecialidad().toLowerCase().contains(texto.toLowerCase()))
                        modeloTabla.addRow(new Object[]{t.getIdTecnico(), t.getNombre(), t.getEspecialidad(), t.getTelefono()});
                }
            }
            default -> cargarDatos();
        }
        if (modeloTabla.getRowCount() == 0)
            JOptionPane.showMessageDialog(this,
                    "No se encontraron resultados con los filtros aplicados.",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
    }

    // ══════════════════════════════════════════════════════
    //  Helpers de UI
    // ══════════════════════════════════════════════════════
    private JDialog crearDialog(String titulo) {
        JDialog d = new JDialog(this, titulo, true);
        d.setSize(420, 380);
        d.setLocationRelativeTo(this);
        d.getContentPane().setBackground(new Color(30, 30, 30));
        d.setLayout(new BorderLayout(10, 10));
        return d;
    }

    /**
     * Crea un panel de formulario con pares (etiqueta, componente).
     * Acepta JComponent para admitir tanto JTextField como JComboBox.
     */
    private JPanel crearPanelForm(Object... parEtiquetaComponente) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 10));
        panel.setBackground(new Color(45, 45, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (int i = 0; i < parEtiquetaComponente.length; i += 2) {
            String etiqueta = (String) parEtiquetaComponente[i];
            JComponent comp = (JComponent) parEtiquetaComponente[i + 1];

            JLabel lbl = new JLabel(etiqueta);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.PLAIN, 12));

            comp.setFont(new Font("Arial", Font.PLAIN, 12));
            if (comp instanceof JTextField tf) {
                tf.setBackground(new Color(60, 60, 60));
                tf.setForeground(Color.WHITE);
                tf.setCaretColor(Color.WHITE);
            } else if (comp instanceof JComboBox<?> cb) {
                cb.setBackground(new Color(60, 60, 60));
                cb.setForeground(Color.WHITE);
            }

            panel.add(lbl);
            panel.add(comp);
        }
        return panel;
    }

    private void mostrarDialog(JDialog dialog, JPanel form, JButton btnGuardar) {
        JButton btnCancelar = new JButton("✖ Cancelar");
        estilizarBoton(btnCancelar, new Color(120, 50, 50));
        btnCancelar.addActionListener(e -> dialog.dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelBotones.setBackground(new Color(30, 30, 30));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        dialog.add(form,         BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostrarResultado(JDialog dialog, boolean ok) {
        if (ok) {
            JOptionPane.showMessageDialog(dialog, "Operación realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(dialog, "No se pudo completar la operación.\nVerifica que el ID no esté duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void error(JDialog parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }

    // ══════════════════════════════════════════════════════
    //  Helpers de datos
    // ══════════════════════════════════════════════════════
    private boolean tieneEstado() {
        return entidad.equals("MAQUINARIA") || entidad.equals("MANTENIMIENTO") || entidad.equals("REPORTE");
    }

    private boolean tieneTipo() {
        return entidad.equals("MAQUINARIA") || entidad.equals("MANTENIMIENTO") || entidad.equals("REPORTE");
    }

    private String[] obtenerOpcionesEstado() {
        return switch (entidad) {
            case "MAQUINARIA"    -> new String[]{"TODOS", "OPERATIVO", "EN_REPARACION", "FUERA_DE_SERVICIO"};
            case "MANTENIMIENTO" -> new String[]{"TODOS", "ACTIVO", "FINALIZADO"};
            case "REPORTE"       -> new String[]{"TODOS", "ABIERTO", "CERRADO"};
            default              -> new String[]{"TODOS"};
        };
    }

    private String[] obtenerOpcionesTipo() {
        return switch (entidad) {
            case "MAQUINARIA"    -> new String[]{"TODOS", "Torno", "Fresadora", "Compresor", "Soldadora", "Taladro CNC", "Rectificadora"};
            case "MANTENIMIENTO" -> new String[]{"TODOS", "Preventivo", "Correctivo"};
            case "REPORTE"       -> new String[]{"TODOS", "Falla", "Mantenimiento"};
            default              -> new String[]{"TODOS"};
        };
    }

    /** Genera un ID simple basado en timestamp — suficiente para datos en memoria */
    private int generarId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }

    private String hoy() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    private String fmtFecha(Date d) {
        if (d == null) return hoy();
        return new SimpleDateFormat("dd/MM/yyyy").format(d);
    }

    private Date parseFecha(JDialog parent, String texto) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(texto);
        } catch (ParseException ex) {
            error(parent, "Formato de fecha inválido. Usa dd/MM/yyyy (ej: 21/04/2026).");
            return null;
        }
    }
}
