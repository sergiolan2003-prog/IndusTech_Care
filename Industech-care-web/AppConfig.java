package com.industech.config;

/**
 * AppConfig — parámetros globales de la aplicación.
 *
 * NOTA: ConexionDB.java lee directamente sus propios valores.
 * Si quieres centralizar la configuración, cámbiala aquí
 * y ajusta ConexionDB para que lea desde esta clase.
 */
public class AppConfig {

    // ── Base de datos ──────────────────────────────────────────────
    public static final String DB_HOST     = "jdbc:mysql://localhost/";
    public static final String DB_NAME     = "industech_bd";
    public static final String DB_USER     = "root";
    public static final String DB_PASS     = "";       // ← ajusta tu contraseña
    public static final String DB_OPTIONS  =
            "?useSSL=false&allowPublicKeyRetrieval=true" +
            "&serverTimezone=America/Bogota&characterEncoding=UTF-8";

    // ── CORS — orígenes permitidos para React y App Móvil ─────────
    public static final String[] CORS_ORIGINS = {
        "http://localhost:3000",   // React (desarrollo)
        "http://localhost:8081"    // App Móvil (React Native / Expo)
    };

    // ── Roles disponibles ──────────────────────────────────────────
    public static final String ROL_ADMIN            = "ADMIN";
    public static final String ROL_JEFE             = "JEFE_MANTENIMIENTO";
    public static final String ROL_TECNICO          = "TECNICO";
    public static final String ROL_CONSULTOR        = "CONSULTOR";

    // ── Umbrales ───────────────────────────────────────────────────
    public static final int UMBRAL_STOCK_BAJO       = 5;   // alerta si stock < este valor

    private AppConfig() {}
}
