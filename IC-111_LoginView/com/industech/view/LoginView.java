package com.industech.view;

import com.industech.controllers.UsuarioController;
import com.industech.models.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * LoginView — pantalla de inicio de sesión.
 *
 * CORRECCIÓN: ahora pide Nombre + Contraseña en vez de ID + Nombre.
 */
public class LoginView extends JFrame {

    private JTextField     txtNombre;
    private JPasswordField txtContrasena;
    private JButton        btnLogin;
    private JLabel         lblMensaje;

    private final UsuarioController usuarioController = new UsuarioController();

    public LoginView() {
        initComponents();
    }

    private void initComponents() {

        setTitle("IndusTech - Iniciar Sesión");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // ── Título ─────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("IndusTech", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(0, 180, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        JLabel lblSub = new JLabel("Sistema de Gestión de Maquinaria", SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 11));
        lblSub.setForeground(Color.GRAY);
        gbc.gridy = 1;
        panel.add(lblSub, gbc);

        // ── Nombre ─────────────────────────────────────────────────
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblNombre = new JLabel("Usuario:");
        lblNombre.setForeground(Color.WHITE);
        panel.add(lblNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        // ── Contraseña ─────────────────────────────────────────────
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        panel.add(lblPass, gbc);

        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtContrasena, gbc);

        // ── Botón Login ────────────────────────────────────────────
        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(0, 180, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        // ── Mensaje ────────────────────────────────────────────────
        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridy = 5;
        panel.add(lblMensaje, gbc);

        add(panel);

        btnLogin.addActionListener(e -> accionLogin());
        getRootPane().setDefaultButton(btnLogin);
    }

    private void accionLogin() {
        String nombre    = txtNombre.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if (nombre.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Por favor completa todos los campos.", Color.ORANGE);
            return;
        }

        Usuario usuario = usuarioController.login(nombre, contrasena);

        if (usuario != null) {
            mostrarMensaje("¡Bienvenido, " + usuario.getNombre() + "!", new Color(0, 200, 100));
            SwingUtilities.invokeLater(() -> {
                new DashboardView(usuario).setVisible(true);
                this.dispose();
            });
        } else {
            mostrarMensaje("Usuario o contraseña incorrectos.", Color.RED);
        }
    }

    private void mostrarMensaje(String texto, Color color) {
        lblMensaje.setText(texto);
        lblMensaje.setForeground(color);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
