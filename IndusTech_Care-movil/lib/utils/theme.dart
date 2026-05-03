import 'package:flutter/material.dart';

// ── Colores ───────────────────────────────────────────────────────────────────
const kPrimary      = Color(0xFF1A56DB);
const kPrimaryDark  = Color(0xFF1E429F);
const kPrimaryLight = Color(0xFFE8F0FE);
const kSidebar      = Color(0xFF0F172A);
const kBg           = Color(0xFFF8FAFC);
const kCard         = Color(0xFFFFFFFF);
const kBorder       = Color(0xFFE2E8F0);
const kText         = Color(0xFF0F172A);
const kTextSec      = Color(0xFF64748B);
const kTextMuted    = Color(0xFF94A3B8);
const kSuccess      = Color(0xFF16A34A);
const kSuccessBg    = Color(0xFFDCFCE7);
const kWarning      = Color(0xFFD97706);
const kWarningBg    = Color(0xFFFEF3C7);
const kDanger       = Color(0xFFDC2626);
const kDangerBg     = Color(0xFFFEE2E2);
const kInfo         = Color(0xFF0891B2);
const kInfoBg       = Color(0xFFCFFAFE);

// ── Tema Material ─────────────────────────────────────────────────────────────
ThemeData buildTheme() {
  return ThemeData(
    useMaterial3: true,
    colorScheme: ColorScheme.fromSeed(
      seedColor: kPrimary,
      background: kBg,
    ),
    scaffoldBackgroundColor: kBg,
    fontFamily: 'Roboto',
    cardTheme: CardThemeData(
      color: kCard,
      elevation: 0,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(14),
        side: const BorderSide(color: kBorder),
      ),
      margin: const EdgeInsets.only(bottom: 10),
    ),
    appBarTheme: const AppBarTheme(
      backgroundColor: kSidebar,
      foregroundColor: Colors.white,
      elevation: 0,
      centerTitle: false,
      titleTextStyle: TextStyle(
        color: Colors.white,
        fontSize: 17,
        fontWeight: FontWeight.w700,
      ),
    ),
    elevatedButtonTheme: ElevatedButtonThemeData(
      style: ElevatedButton.styleFrom(
        backgroundColor: kPrimary,
        foregroundColor: Colors.white,
        elevation: 0,
        padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 14),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        textStyle: const TextStyle(fontSize: 14, fontWeight: FontWeight.w700),
      ),
    ),
    inputDecorationTheme: InputDecorationTheme(
      filled: true,
      fillColor: kCard,
      border: OutlineInputBorder(
        borderRadius: BorderRadius.circular(10),
        borderSide: const BorderSide(color: kBorder, width: 1.5),
      ),
      enabledBorder: OutlineInputBorder(
        borderRadius: BorderRadius.circular(10),
        borderSide: const BorderSide(color: kBorder, width: 1.5),
      ),
      focusedBorder: OutlineInputBorder(
        borderRadius: BorderRadius.circular(10),
        borderSide: const BorderSide(color: kPrimary, width: 2),
      ),
      contentPadding: const EdgeInsets.symmetric(horizontal: 14, vertical: 13),
      hintStyle: const TextStyle(color: kTextMuted, fontSize: 14),
    ),
    bottomNavigationBarTheme: const BottomNavigationBarThemeData(
      backgroundColor: kSidebar,
      selectedItemColor: kPrimary,
      unselectedItemColor: Color(0xFF475569),
      type: BottomNavigationBarType.fixed,
      elevation: 0,
    ),
  );
}

// ── Badge helpers ─────────────────────────────────────────────────────────────
Color estadoBadgeColor(String estado) {
  switch (estado) {
    case 'OPERATIVO':         return kSuccess;
    case 'EN_REPARACION':     return kWarning;
    case 'FUERA_DE_SERVICIO': return kDanger;
    case 'ACTIVO':            return kWarning;
    case 'FINALIZADO':        return kSuccess;
    case 'CANCELADO':         return kDanger;
    default:                  return kTextMuted;
  }
}

Color gravedadColor(String g) {
  switch (g) {
    case 'ALTA':    return kDanger;
    case 'CRITICA': return kDanger;
    case 'MEDIA':   return kWarning;
    case 'BAJA':    return kSuccess;
    default:        return kTextMuted;
  }
}