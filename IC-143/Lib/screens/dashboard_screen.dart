// ════════════════════════════════════════════════════════════════════════════
// dashboard_screen.dart
// ════════════════════════════════════════════════════════════════════════════
import 'package:flutter/material.dart';
import '../models/usuario.dart';
import '../models/models.dart';
import '../services/api_service.dart';
import '../utils/theme.dart';
import '../widgets/common_widgets.dart';

class DashboardScreen extends StatefulWidget {
  final Usuario? usuario;
  final Function(int) onNavigate;
  const DashboardScreen({super.key, this.usuario, required this.onNavigate});

  @override
  State<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  Map<String, int> _stats = {};
  List<Mantenimiento> _mantenimientos = [];
  List<Falla> _fallas = [];
  List<Alerta> _alertas = [];
  bool _loading = true;

  @override
  void initState() { super.initState(); _load(); }

  Future<void> _load() async {
    try {
      // Cada llamada es independiente — si una falla por permisos, las demás siguen
      final results = await Future.wait([
        ApiService.getMaquinarias().catchError((_) => <Maquinaria>[]),
        ApiService.getMantenimientos().catchError((_) => <Mantenimiento>[]),
        ApiService.getFallas().catchError((_) => <Falla>[]),
        ApiService.getAlertas().catchError((_) => <Alerta>[]),
      ]);
      final maquinas  = results[0] as List<Maquinaria>;
      final mantenims = results[1] as List<Mantenimiento>;
      final fallas    = results[2] as List<Falla>;
      final alertas   = results[3] as List<Alerta>;
      setState(() {
        _stats = {
          'maquinas':  maquinas.length,
          'activos':   mantenims.where((m) => m.estado == 'ACTIVO').length,
          'fallas':    fallas.where((f) => f.activa).length,
          'alertas':   alertas.where((a) => !a.leida).length,
        };
        _mantenimientos = mantenims.where((m) => m.estado == 'ACTIVO').take(3).toList();
        _fallas  = fallas.where((f) => f.activa).take(3).toList();
        _alertas = alertas.where((a) => a.activa).take(3).toList();
        _loading = false;
      });
    } catch (_) { setState(() => _loading = false); }
  }

  @override
  Widget build(BuildContext context) {
    if (_loading) return const Center(child: CircularProgressIndicator());
    return RefreshIndicator(
      onRefresh: _load,
      child: SingleChildScrollView(
        physics: const AlwaysScrollableScrollPhysics(),
        padding: const EdgeInsets.all(16),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          // Hero
          Card(
            color: kSidebar,
            child: Padding(
              padding: const EdgeInsets.all(20),
              child: Row(children: [
                CircleAvatar(radius: 24, backgroundColor: kPrimary.withOpacity(0.3),
                    child: Text(widget.usuario?.initials ?? 'U',
                        style: const TextStyle(color: kPrimary, fontWeight: FontWeight.w900, fontSize: 16))),
                const SizedBox(width: 14),
                Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                  const Text('Bienvenido', style: TextStyle(color: Color(0xFF64748B), fontSize: 12)),
                  Text(widget.usuario?.nombre ?? '', style: const TextStyle(color: Colors.white, fontWeight: FontWeight.w800, fontSize: 15)),
                  Container(
                    margin: const EdgeInsets.only(top: 4),
                    padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 3),
                    decoration: BoxDecoration(color: kPrimary.withOpacity(0.25), borderRadius: BorderRadius.circular(99)),
                    child: Text(widget.usuario?.rolLabel ?? '', style: const TextStyle(color: kPrimary, fontSize: 11, fontWeight: FontWeight.w700)),
                  ),
                ])),
              ]),
            ),
          ),
          const SizedBox(height: 16),

          // Stats
          Row(children: [
            StatCard(emoji: '⚙️', value: '${_stats['maquinas']}', label: 'Máquinas',   color: kPrimary),
            const SizedBox(width: 8),
            StatCard(emoji: '🔧', value: '${_stats['activos']}',  label: 'En proceso', color: kWarning),
            const SizedBox(width: 8),
            StatCard(emoji: '⚠️', value: '${_stats['fallas']}',   label: 'Fallas',     color: kDanger),
            const SizedBox(width: 8),
            StatCard(emoji: '🔔', value: '${_stats['alertas']}',  label: 'Alertas',    color: const Color(0xFF7C3AED)),
          ]),
          const SizedBox(height: 20),

          // Accesos rápidos
          AppCard(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            const SectionHeader(title: 'Accesos rápidos'),
            const SizedBox(height: 14),
            GridView.count(
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              crossAxisCount: 3,
              childAspectRatio: 1.1,
              crossAxisSpacing: 10,
              mainAxisSpacing: 10,
              children: [
                QuickButton(emoji: '⚙️', label: 'Máquinas',    color: kPrimary,  onTap: () => widget.onNavigate(1)),
                QuickButton(emoji: '🔧', label: 'Mantenimie.', color: kInfo,     onTap: () => widget.onNavigate(2)),
                QuickButton(emoji: '⚠️', label: 'Fallas',      color: kDanger,   onTap: () => widget.onNavigate(3)),
                QuickButton(emoji: '📦', label: 'Repuestos',   color: kSuccess,  onTap: () => widget.onNavigate(4)),
                QuickButton(emoji: '🔔', label: 'Alertas',     color: const Color(0xFF7C3AED), onTap: () => widget.onNavigate(5)),
              ],
            ),
          ])),
          const SizedBox(height: 16),

          // Mantenimientos activos
          if (_mantenimientos.isNotEmpty) ...[
            SectionHeader(title: 'Mantenimientos activos', action: 'Ver todos', onAction: () => widget.onNavigate(2)),
            const SizedBox(height: 10),
            ..._mantenimientos.map((m) => Card(
              child: ListTile(
                leading: const CircleAvatar(backgroundColor: kWarningBg, child: Text('🔧')),
                title: Text(m.descripcion ?? 'Mantenimiento #${m.idMantenimiento}',
                    style: const TextStyle(fontSize: 13, fontWeight: FontWeight.w600), maxLines: 1, overflow: TextOverflow.ellipsis),
                subtitle: Text('${m.tipo} · Maq. #${m.idMaquinaria}', style: const TextStyle(fontSize: 12, color: kTextSec)),
                trailing: StatusBadge(label: m.estado, color: estadoBadgeColor(m.estado)),
              ),
            )),
            const SizedBox(height: 16),
          ],

          // Fallas activas
          if (_fallas.isNotEmpty) ...[
            SectionHeader(title: 'Fallas activas', action: 'Ver todas', onAction: () => widget.onNavigate(3)),
            const SizedBox(height: 10),
            ..._fallas.map((f) => Card(
              child: ListTile(
                leading: const CircleAvatar(backgroundColor: kDangerBg, child: Text('⚠️')),
                title: Text(f.descripcion, style: const TextStyle(fontSize: 13, fontWeight: FontWeight.w600), maxLines: 1, overflow: TextOverflow.ellipsis),
                subtitle: Text('Maq. #${f.idMaquinaria} · ${f.fecha ?? '—'}', style: const TextStyle(fontSize: 12, color: kTextSec)),
                trailing: StatusBadge(label: f.gravedad, color: gravedadColor(f.gravedad)),
              ),
            )),
            const SizedBox(height: 16),
          ],

          // Alertas
          if (_alertas.isNotEmpty) ...[
            SectionHeader(title: 'Alertas recientes', action: 'Ver todas', onAction: () => widget.onNavigate(5)),
            const SizedBox(height: 10),
            ..._alertas.map((a) => Container(
              margin: const EdgeInsets.only(bottom: 8),
              padding: const EdgeInsets.all(14),
              decoration: BoxDecoration(
                color: const Color(0xFFFFFBEA),
                borderRadius: BorderRadius.circular(12),
                border: Border.all(color: const Color(0xFFFDE68A)),
              ),
              child: Row(children: [
                Text(a.emoji, style: const TextStyle(fontSize: 20)),
                const SizedBox(width: 10),
                Expanded(child: Text(a.mensaje, style: const TextStyle(fontSize: 12, color: kText), maxLines: 2)),
                if (!a.leida) Container(
                  width: 8, height: 8,
                  decoration: const BoxDecoration(color: kDanger, shape: BoxShape.circle),
                ),
              ]),
            )),
          ],
          const SizedBox(height: 20),
        ]),
      ),
    );
  }
}