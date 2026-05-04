package com.industech.legacy.view;

import com.industech.models.Usuario;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    private Usuario usuarioActual;

    public DashboardView(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
    }

    private void initComponents() {

        // ── Configuración ventana ──────────────────────────
        setTitle("IndusTech - Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Panel principal ────────────────────────────────
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(20, 20, 20));

        // ── Header ─────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 120, 180));
        header.setPreferredSize(new Dimension(700, 70));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblBienvenido = new JLabel(
                "Bienvenido, " + usuarioActual.getNombre() +
                        "  |  Rol: " + formatearRol(usuarioActual.getRol()
        ));
        lblBienvenido.setForeground(Color.WHITE);
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBackground(new Color(200, 50, 50));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        header.add(lblBienvenido, BorderLayout.WEST);
        header.add(btnCerrarSesion, BorderLayout.EAST);

        // ── Panel de botones (menú) ────────────────────────
        JPanel panelMenu = new JPanel(new GridLayout(3, 3, 15, 15));
        panelMenu.setBackground(new Color(20, 20, 20));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Los botones se muestran según el rol
        agregarBotonesSegunRol(panelMenu);

        // ── Footer ─────────────────────────────────────────
        JLabel lblFooter = new JLabel("IndusTech © 2024", SwingConstants.CENTER);
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 10));
        lblFooter.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        panelPrincipal.add(header,    BorderLayout.NORTH);
        panelPrincipal.add(panelMenu, BorderLayout.CENTER);
        panelPrincipal.add(lblFooter, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void agregarBotonesSegunRol(JPanel panel) {
        String rol = usuarioActual.getRol();

        // ADMIN ve todo
        if (rol.equals("ADMIN")) {
            panel.add(crearBoton("🔧 Maquinaria",    new Color(0, 120, 180),  () -> abrirGestion("MAQUINARIA")));
            panel.add(crearBoton("👷 Técnicos",       new Color(0, 150, 100),  () -> abrirGestion("TECNICO")));
            panel.add(crearBoton("📋 Reportes",       new Color(180, 100, 0),  () -> abrirGestion("REPORTE")));
            panel.add(crearBoton("⚠️ Fallas",         new Color(180, 50, 50),  () -> abrirGestion("FALLA")));
            panel.add(crearBoton("🛠 Mantenimientos", new Color(100, 0, 180),  () -> abrirGestion("MANTENIMIENTO")));
            panel.add(crearBoton("👥 Usuarios",       new Color(50, 50, 180),  () -> abrirGestion("USUARIO")));
            panel.add(crearBoton("📡 Estado Equipos", new Color(0, 140, 130),  () -> abrirEstadoEquipos()));
            panel.add(crearBoton("🔩 Repuestos", new Color(120, 60, 0), () -> abrirGestion("REPUESTO")));

        }

        // ── JEFE DE MANTENIMIENTO: programa, asigna, consulta ─
        else if (rol.equals("JEFE_MANTENIMIENTO")) {
            panel.add(crearBoton("🛠 Mantenimientos",  new Color(100, 0, 180),  () -> abrirGestion("MANTENIMIENTO")));
            panel.add(crearBoton("👷 Técnicos",        new Color(0, 150, 100),  () -> abrirGestion("TECNICO")));
            panel.add(crearBoton("🔧 Consultar Maq.", new Color(0, 120, 180),  () -> abrirGestion("MAQUINARIA")));
            panel.add(crearBoton("⚠️ Fallas",          new Color(180, 50, 50),  () -> abrirGestion("FALLA")));
            panel.add(crearBoton("📋 Reportes",        new Color(180, 100, 0),  () -> abrirGestion("REPORTE")));
            panel.add(crearBoton("📡 Estado Equipos",  new Color(0, 140, 130),  () -> abrirEstadoEquipos()));
            panel.add(crearBoton("👷 Asignar Técnicos", new Color(70, 0, 160), () -> abrirAsignacion()));
            panel.add(crearBoton("🔩 Repuestos", new Color(120, 60, 0), () -> abrirGestion("REPUESTO")));
        }

        // TECNICO ve solo lo suyo
        else if (rol.equals("TECNICO")) {
            panel.add(crearBoton("🔧 Maquinaria",    new Color(0, 120, 180),  () -> abrirGestion("MAQUINARIA")));
            panel.add(crearBoton("⚠️ Fallas",        new Color(180, 50, 50),  () -> abrirGestion("FALLA")));
            panel.add(crearBoton("🛠 Mantenimientos",new Color(100, 0, 180),  () -> abrirGestion("MANTENIMIENTO")));
            panel.add(crearBoton("📡 Estado Equipos",new Color(0, 140, 130),  () -> abrirEstadoEquipos()));
        }

        // CLIENTE solo reportes
        else if (rol.equals("CONSULTOR")) {
            panel.add(crearBoton("⚠️ Reportar Falla",  new Color(180, 100, 0),  () -> abrirGestion("FALLA")));
        }
    }

    // Crea un botón estilizado con acción lambda
    private JButton crearBoton(String texto, Color color, Runnable accion) {
        JButton btn = new JButton("<html><center>" + texto + "</center></html>");
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        btn.addActionListener(e -> accion.run());
        return btn;
    }

    private void abrirGestion(String entidad) {
        new GestionView(entidad, usuarioActual).setVisible(true);
    }

    private void abrirEstadoEquipos() {
        new EstadoEquiposView(usuarioActual).setVisible(true);
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas cerrar sesión?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            new LoginView().setVisible(true);
            this.dispose();
        }
    }

    private void abrirAsignacion() {
        new AsignacionTecnicoView(null, null, null, null).setVisible(true);
        // Nota: se pasa null para abrir en modo "selector de orden"
    }

    private String formatearRol(String rol) {
        return switch (rol) {
            case "ADMIN"               -> "Administrador";
            case "JEFE_MANTENIMIENTO"  -> "Jefe de Mantenimiento";
            case "TECNICO"             -> "Técnico";
            case "CONSULTOR"           -> "Consultor / Operario";
            default                    -> rol;
        };
    }
}
