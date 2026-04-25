package com.industech.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConexionDB —  conexiones  MariaDB/MySQL.
 * Cada llamada a getConexion() entrega una nueva Connection que
 * el bloque try-with-resources cierra automáticamente.
 *

 */
public class ConexionDB {

    private static final String URL  =
            "jdbc:mysql://localhost/industech_bd"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=America/Bogota"
            + "&characterEncoding=UTF-8";

    private static final String USER = "root";
    private static final String PASS = "";   // ajusta tu contraseña aquí

    private ConexionDB() {}

    /**
     * Devuelve una conexión nueva cada vez.

     *   try (Connection con = ConexionDB.getConexion()) { ... }
     *
     * @throws RuntimeException si no puede conectar
     */
    public static Connection getConexion() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            return con;
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión a la BD: " + e.getMessage());
            throw new RuntimeException("No se pudo conectar a industech_bd: " + e.getMessage(), e);
        }
    }

    /** Intento de conexión de prueba — útil al arrancar Main. */
    public static boolean probarConexion() {
        try (Connection con = getConexion()) {
            System.out.println("✅ Conexión exitosa a industech_bd");
            return true;
        } catch (Exception e) {
            System.err.println("❌ Fallo en prueba de conexión: " + e.getMessage());
            return false;
        }
    }
}
