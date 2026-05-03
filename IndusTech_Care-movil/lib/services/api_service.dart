import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../models/usuario.dart';
import '../models/models.dart';

// ─── IMPORTANTE ───────────────────────────────────────────────────────────────
// Cambia esta IP por la IP de tu computador en la red local.
// En PowerShell: ipconfig → busca "Dirección IPv4"
const String BASE_URL = 'https://industech-care-backend-production.up.railway.app/api';

class ApiService {
  static String? _token;

  // ── Token ──────────────────────────────────────────────────────────────────
  static Future<void> saveToken(String token) async {
    _token = token;
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('token', token);
  }

  static Future<String?> getToken() async {
    if (_token != null) return _token;
    final prefs = await SharedPreferences.getInstance();
    _token = prefs.getString('token');
    return _token;
  }

  static Future<void> clearToken() async {
    _token = null;
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('token');
    await prefs.remove('usuario');
  }

  static Future<Map<String, String>> _headers() async {
    final token = await getToken();
    return {
      'Content-Type': 'application/json',
      if (token != null) 'Authorization': 'Bearer $token',
    };
  }

  static Future<dynamic> _get(String path) async {
    final res = await http.get(
      Uri.parse('$BASE_URL$path'),
      headers: await _headers(),
    ).timeout(const Duration(seconds: 10));
    if (res.statusCode == 403 || res.statusCode == 401) {
      throw Exception('Sin permisos para acceder a este recurso');
    }
    final raw = utf8.decode(res.bodyBytes).trim();
    if (raw.isEmpty) return {'data': []};
    return jsonDecode(raw);
  }

  static Future<dynamic> _post(String path, Map<String, dynamic> body) async {
    final res = await http.post(
      Uri.parse('$BASE_URL$path'),
      headers: await _headers(),
      body: jsonEncode(body),
    ).timeout(const Duration(seconds: 10));
    return jsonDecode(utf8.decode(res.bodyBytes));
  }

  // ── Auth ───────────────────────────────────────────────────────────────────
  static Future<Usuario> login(String nombre, String contrasena) async {
    final res = await _post('/auth/login', {
      'nombre': nombre,
      'contrasena': contrasena,
    });
    if (res['success'] == true) {
      final data = res['data'];
      final usuario = Usuario.fromJson(data);
      await saveToken(usuario.token);
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('usuario', jsonEncode(data));
      return usuario;
    }
    throw Exception(res['message'] ?? 'Credenciales inválidas');
  }

  static Future<Usuario?> getSavedUser() async {
    final prefs = await SharedPreferences.getInstance();
    final stored = prefs.getString('usuario');
    if (stored == null) return null;
    return Usuario.fromJson(jsonDecode(stored));
  }

  static Future<dynamic> _put(String path, [Map<String, dynamic>? body]) async {
    final res = await http.put(
      Uri.parse('$BASE_URL$path'),
      headers: await _headers(),
      body: body != null ? jsonEncode(body) : null,
    ).timeout(const Duration(seconds: 10));

    // Lanza excepción si el servidor rechaza la operación
    if (res.statusCode == 401 || res.statusCode == 403) {
      throw Exception('No tienes permisos para realizar esta operación');
    }
    if (res.statusCode >= 400) {
      throw Exception('Error del servidor (${res.statusCode})');
    }

    // Maneja respuesta vacía sin crash
    final raw = utf8.decode(res.bodyBytes).trim();
    if (raw.isEmpty) return {'success': true};
    try {
      return jsonDecode(raw);
    } catch (_) {
      return {'success': true};
    }
  }

  // ── Maquinarias ────────────────────────────────────────────────────────────
  static Future<List<Maquinaria>> getMaquinarias() async {
    final res = await _get('/maquinarias');
    final List data = res['data'] ?? [];
    return data.map((j) => Maquinaria.fromJson(j)).toList();
  }

  // El estado va como query param — hay que construir la URI correctamente
  static Future<void> cambiarEstadoMaquinaria(int id, String estado) async {
    final uri = Uri.parse('$BASE_URL/maquinarias/$id/estado')
        .replace(queryParameters: {'nuevoEstado': estado});
    final headers = await _headers();
    final res = await http.put(uri, headers: headers)
        .timeout(const Duration(seconds: 10));
    if (res.statusCode == 401 || res.statusCode == 403) {
      throw Exception('No tienes permisos para cambiar el estado');
    }
    if (res.statusCode >= 400) {
      throw Exception('Error al cambiar estado (${res.statusCode})');
    }
  }

  // ── Mantenimientos ─────────────────────────────────────────────────────────
  static Future<List<Mantenimiento>> getMantenimientos() async {
    final res = await _get('/mantenimientos');
    final List data = res['data'] ?? [];
    return data.map((j) => Mantenimiento.fromJson(j)).toList();
  }

  static Future<void> finalizarMantenimiento(int id) async {
    await _put('/mantenimientos/$id/finalizar');
  }

  static Future<void> crearMantenimiento(Mantenimiento m) async {
    await _post('/mantenimientos', m.toJson());
  }

  // ── Fallas ─────────────────────────────────────────────────────────────────
  static Future<List<Falla>> getFallas() async {
    final res = await _get('/fallas');
    final List data = res['data'] ?? [];
    return data.map((j) => Falla.fromJson(j)).toList();
  }

  static Future<void> reportarFalla(Falla f) async {
    await _post('/fallas', f.toJson());
  }

  static Future<void> cerrarFalla(int id) async {
    await _put('/fallas/$id/cerrar');
  }

  // ── Repuestos ──────────────────────────────────────────────────────────────
  static Future<List<Repuesto>> getRepuestos() async {
    final res = await _get('/repuestos');
    final List data = res['data'] ?? [];
    return data.map((j) => Repuesto.fromJson(j)).toList();
  }

  // ── Alertas ────────────────────────────────────────────────────────────────
  static Future<List<Alerta>> getAlertas() async {
    final res = await _get('/alertas');
    final List data = res['data'] ?? [];
    return data.map((j) => Alerta.fromJson(j)).toList();
  }

  static Future<void> desactivarAlerta(int id) async {
    await _put('/alertas/$id/desactivar');
  }
}