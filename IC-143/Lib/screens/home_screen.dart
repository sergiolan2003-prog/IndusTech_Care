import 'package:flutter/material.dart';
import '../models/usuario.dart';
import '../services/api_service.dart';
import '../utils/theme.dart';
import 'dashboard_screen.dart';
import 'feature_screens.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _currentIndex = 0;
  Usuario? _usuario;

  final List<_TabItem> _tabs = [
    _TabItem('Inicio',    Icons.home_outlined,                    Icons.home),
    _TabItem('Máquinas',  Icons.precision_manufacturing_outlined, Icons.precision_manufacturing),
    _TabItem('Mant.',     Icons.build_outlined,                   Icons.build),
    _TabItem('Fallas',    Icons.warning_amber_outlined,           Icons.warning_amber),
    _TabItem('Repuestos', Icons.inventory_2_outlined,             Icons.inventory_2),
    _TabItem('Alertas',   Icons.notifications_outlined,           Icons.notifications),
  ];

  @override
  void initState() {
    super.initState();
    _loadUser();
  }

  Future<void> _loadUser() async {
    final u = await ApiService.getSavedUser();
    setState(() => _usuario = u);
  }

  Future<void> _logout() async {
    await ApiService.clearToken();
    if (mounted) Navigator.pushReplacementNamed(context, '/login');
  }

  Widget _buildScreen() {
    switch (_currentIndex) {
      case 0:  return DashboardScreen(usuario: _usuario, onNavigate: (i) => setState(() => _currentIndex = i));
      case 1:  return MaquinariasScreen();
      case 2:  return MantenimientosScreen();
      case 3:  return FallasScreen();
      case 4:  return RepuestosScreen();
      case 5:  return AlertasScreen();
      default: return DashboardScreen(usuario: _usuario, onNavigate: (i) => setState(() => _currentIndex = i));
    }
  }

  @override
  Widget build(BuildContext context) {
    final initials = _usuario?.initials ?? 'U';
    final rolLabel  = _usuario?.rolLabel  ?? '';

    return Scaffold(
      appBar: AppBar(
        title: Text(_tabs[_currentIndex].label),
        actions: [
          Padding(
            padding: const EdgeInsets.only(right: 16),
            child: GestureDetector(
              onTap: () => _showUserMenu(context),
              child: Row(children: [
                CircleAvatar(
                  radius: 16,
                  backgroundColor: kPrimary,
                  child: Text(initials,
                      style: const TextStyle(color: Colors.white, fontSize: 12, fontWeight: FontWeight.w700)),
                ),
                const SizedBox(width: 8),
                Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(_usuario?.nombre ?? '',
                        style: const TextStyle(fontSize: 12, fontWeight: FontWeight.w700, color: Colors.white)),
                    Text(rolLabel,
                        style: const TextStyle(fontSize: 10, color: Color(0xFF94A3B8))),
                  ],
                ),
                const SizedBox(width: 4),
                const Icon(Icons.keyboard_arrow_down, color: Color(0xFF94A3B8), size: 16),
              ]),
            ),
          ),
        ],
      ),
      body: _buildScreen(),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: (i) => setState(() => _currentIndex = i),
        items: _tabs.map((t) => BottomNavigationBarItem(
          icon: Icon(t.icon),
          activeIcon: Icon(t.activeIcon),
          label: t.label,
        )).toList(),
      ),
    );
  }

  void _showUserMenu(BuildContext context) {
    showModalBottomSheet(
      context: context,
      shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.vertical(top: Radius.circular(20))),
      builder: (_) => Padding(
        padding: const EdgeInsets.all(24),
        child: Column(mainAxisSize: MainAxisSize.min, children: [
          CircleAvatar(
              radius: 28, backgroundColor: kPrimary,
              child: Text(_usuario?.initials ?? 'U',
                  style: const TextStyle(color: Colors.white, fontSize: 20, fontWeight: FontWeight.w700))),
          const SizedBox(height: 12),
          Text(_usuario?.nombre ?? '',
              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w700)),
          Text(_usuario?.rolLabel ?? '',
              style: const TextStyle(color: kTextMuted, fontSize: 13)),
          const SizedBox(height: 24),
          ListTile(
            leading: const Icon(Icons.logout, color: kDanger),
            title: const Text('Cerrar sesión',
                style: TextStyle(color: kDanger, fontWeight: FontWeight.w600)),
            onTap: () { Navigator.pop(context); _logout(); },
          ),
        ]),
      ),
    );
  }
}

class _TabItem {
  final String label;
  final IconData icon;
  final IconData activeIcon;
  _TabItem(this.label, this.icon, this.activeIcon);
}