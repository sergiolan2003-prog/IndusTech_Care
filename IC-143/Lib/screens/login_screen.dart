import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../utils/theme.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _nombreCtrl    = TextEditingController();
  final _passCtrl      = TextEditingController();
  bool _showPass       = false;
  bool _loading        = false;
  String? _error;

  Future<void> _login() async {
    if (_nombreCtrl.text.isEmpty || _passCtrl.text.isEmpty) {
      setState(() => _error = 'Completa todos los campos');
      return;
    }
    setState(() { _loading = true; _error = null; });
    try {
      final usuario = await ApiService.login(
        _nombreCtrl.text.trim(),
        _passCtrl.text.trim(),
      );
      // CONSULTOR = rol de solo lectura (equivalente a OPERARIO en campo)
      // ADMIN no puede entrar desde la app móvil
      const rolesPermitidos = ['TECNICO', 'CONSULTOR', 'JEFE_MANTENIMIENTO'];
      if (!rolesPermitidos.contains(usuario.rol)) {
        setState(() { _error = 'Los administradores deben usar la versión web.'; _loading = false; });
        return;
      }
      if (mounted) Navigator.pushReplacementNamed(context, '/home');
    } catch (e) {
      setState(() { _error = e.toString().replaceAll('Exception: ', ''); });
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Row(
        children: [
          // Panel izquierdo oscuro
          Expanded(
            flex: 4,
            child: Container(
              color: kSidebar,
              padding: const EdgeInsets.all(32),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.end,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    width: 56, height: 56,
                    decoration: BoxDecoration(color: kPrimary, borderRadius: BorderRadius.circular(14)),
                    child: const Icon(Icons.settings, color: Colors.white, size: 28),
                  ),
                  const SizedBox(height: 16),
                  RichText(text: const TextSpan(
                    style: TextStyle(fontSize: 22, fontWeight: FontWeight.w900, letterSpacing: -0.5),
                    children: [
                      TextSpan(text: 'INDUSTECH ', style: TextStyle(color: Colors.white)),
                      TextSpan(text: 'CARE', style: TextStyle(color: Color(0xFF60A5FA))),
                    ],
                  )),
                  const SizedBox(height: 8),
                  const Text('Sistema de Gestión de\nMantenimiento Industrial',
                      style: TextStyle(color: Color(0xFF64748B), fontSize: 12, height: 1.5)),
                  const SizedBox(height: 24),
                  Row(children: [
                    _statItem('24', 'Máquinas'),
                    const SizedBox(width: 24),
                    _statItem('56', 'Mant. activos'),
                    const SizedBox(width: 24),
                    _statItem('8', 'Técnicos'),
                  ]),
                  const SizedBox(height: 40),
                ],
              ),
            ),
          ),

          // Panel derecho — formulario
          Expanded(
            flex: 6,
            child: Container(
              color: kBg,
              padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 32),
              child: Center(
                child: ConstrainedBox(
                  constraints: const BoxConstraints(maxWidth: 400),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text('Iniciar sesión',
                          style: TextStyle(fontSize: 26, fontWeight: FontWeight.w900, color: kText)),
                      const SizedBox(height: 4),
                      const Text('Ingresa tus credenciales para continuar',
                          style: TextStyle(fontSize: 14, color: kTextMuted)),
                      const SizedBox(height: 32),

                      // Usuario
                      const Text('USUARIO', style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, color: kText, letterSpacing: 0.5)),
                      const SizedBox(height: 7),
                      TextField(
                        controller: _nombreCtrl,
                        decoration: const InputDecoration(
                          hintText: 'Ingrese su usuario',
                          prefixIcon: Icon(Icons.person_outline, color: kTextMuted),
                        ),
                        onSubmitted: (_) => _login(),
                      ),
                      const SizedBox(height: 18),

                      // Contraseña
                      const Text('CONTRASEÑA', style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, color: kText, letterSpacing: 0.5)),
                      const SizedBox(height: 7),
                      TextField(
                        controller: _passCtrl,
                        obscureText: !_showPass,
                        decoration: InputDecoration(
                          hintText: 'Ingrese su contraseña',
                          prefixIcon: const Icon(Icons.lock_outline, color: kTextMuted),
                          suffixIcon: IconButton(
                            icon: Icon(_showPass ? Icons.visibility_off : Icons.visibility, color: kTextMuted),
                            onPressed: () => setState(() => _showPass = !_showPass),
                          ),
                        ),
                        onSubmitted: (_) => _login(),
                      ),
                      const SizedBox(height: 24),

                      // Error
                      if (_error != null) ...[
                        Container(
                          padding: const EdgeInsets.all(12),
                          decoration: BoxDecoration(
                            color: kDangerBg,
                            borderRadius: BorderRadius.circular(10),
                            border: Border.all(color: kDanger.withOpacity(0.3)),
                          ),
                          child: Text(_error!, style: const TextStyle(color: kDanger, fontSize: 13)),
                        ),
                        const SizedBox(height: 16),
                      ],

                      // Botón
                      SizedBox(
                        width: double.infinity,
                        child: ElevatedButton(
                          onPressed: _loading ? null : _login,
                          child: _loading
                              ? const SizedBox(height: 20, width: 20, child: CircularProgressIndicator(color: Colors.white, strokeWidth: 2))
                              : const Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [Icon(Icons.login, size: 18), SizedBox(width: 8), Text('Ingresar')],
                          ),
                        ),
                      ),

                      const SizedBox(height: 32),
                      Container(
                        padding: const EdgeInsets.all(14),
                        decoration: BoxDecoration(
                          color: const Color(0xFFF0F9FF),
                          borderRadius: BorderRadius.circular(10),
                          border: Border.all(color: const Color(0xFFBAE6FD)),
                        ),
                        child: const Text(
                          'Esta app está disponible solo para técnicos y operarios. Administradores deben usar la versión web.',
                          style: TextStyle(fontSize: 12, color: Color(0xFF0369A1)),
                          textAlign: TextAlign.center,
                        ),
                      ),

                      const SizedBox(height: 24),
                      const Center(child: Text('© 2024 INDUSTECH CARE',
                          style: TextStyle(fontSize: 11, color: kTextMuted))),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _statItem(String value, String label) => Column(
    crossAxisAlignment: CrossAxisAlignment.start,
    children: [
      Text(value, style: const TextStyle(color: Colors.white, fontSize: 22, fontWeight: FontWeight.w900)),
      Text(label, style: const TextStyle(color: Color(0xFF64748B), fontSize: 11)),
    ],
  );
}