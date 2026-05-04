import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'services/api_service.dart';
import 'utils/theme.dart';
import 'screens/login_screen.dart';
import 'screens/home_screen.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp,
    DeviceOrientation.landscapeLeft,
    DeviceOrientation.landscapeRight,
  ]);
  runApp(const IndusTechApp());
}

class IndusTechApp extends StatelessWidget {
  const IndusTechApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'IndusTech Care',
      debugShowCheckedModeBanner: false,
      theme: buildTheme(),
      home: const SplashScreen(),
      routes: {
        '/login': (_) => const LoginScreen(),
        '/home':  (_) => const HomeScreen(),
      },
    );
  }
}

// ── Splash — verifica si hay sesión guardada ──────────────────────────────────
class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    _checkSession();
  }

  Future<void> _checkSession() async {
    await Future.delayed(const Duration(milliseconds: 800));
    final usuario = await ApiService.getSavedUser();
    if (!mounted) return;
    if (usuario != null && ['TECNICO','CONSULTOR','JEFE_MANTENIMIENTO'].contains(usuario.rol)) {
      Navigator.pushReplacementNamed(context, '/home');
    } else {
      Navigator.pushReplacementNamed(context, '/login');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: kSidebar,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              width: 80, height: 80,
              decoration: BoxDecoration(color: kPrimary, borderRadius: BorderRadius.circular(20)),
              child: const Icon(Icons.settings, color: Colors.white, size: 42),
            ),
            const SizedBox(height: 20),
            RichText(text: const TextSpan(
              style: TextStyle(fontSize: 26, fontWeight: FontWeight.w900, letterSpacing: -0.5),
              children: [
                TextSpan(text: 'INDUSTECH ', style: TextStyle(color: Colors.white)),
                TextSpan(text: 'CARE', style: TextStyle(color: Color(0xFF60A5FA))),
              ],
            )),
            const SizedBox(height: 8),
            const Text('Sistema de Gestión de Mantenimiento',
                style: TextStyle(color: Color(0xFF64748B), fontSize: 13)),
            const SizedBox(height: 40),
            const CircularProgressIndicator(color: kPrimary),
          ],
        ),
      ),
    );
  }
}