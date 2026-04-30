// ════════════════════════════════════════════════════════════════════════════
// maquinarias_screen.dart
// ════════════════════════════════════════════════════════════════════════════
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import '../models/models.dart';
import '../services/api_service.dart';
import '../utils/theme.dart';
import '../widgets/common_widgets.dart';

// Helper global para verificar rol del usuario guardado
Future<String> _getUserRol() async {
  final prefs = await SharedPreferences.getInstance();
  final stored = prefs.getString('usuario');
  if (stored == null) return '';
  final data = jsonDecode(stored);
  return data['rol'] ?? '';
}

// Roles que pueden REPORTAR fallas (crear)
bool _canWrite(String rol) => ['TECNICO', 'JEFE_MANTENIMIENTO', 'CONSULTOR'].contains(rol);

// Roles que pueden CERRAR fallas y FINALIZAR mantenimientos
bool _canClose(String rol) => ['TECNICO', 'JEFE_MANTENIMIENTO'].contains(rol);

// Solo JEFE_MANTENIMIENTO puede cambiar estado de maquinaria
bool _canChangeMaquinaEstado(String rol) => rol == 'JEFE_MANTENIMIENTO';

class MaquinariasScreen extends StatefulWidget {
  const MaquinariasScreen({super.key});
  @override State<MaquinariasScreen> createState() => _MaquinariasScreenState();
}

class _MaquinariasScreenState extends State<MaquinariasScreen> {
  List<Maquinaria> _items = [];
  List<Maquinaria> _filtered = [];
  bool _loading = true;
  String _rol = '';
  final _searchCtrl = TextEditingController();

  @override void initState() { super.initState(); _loadAll(); }

  Future<void> _loadAll() async {
    _rol = await _getUserRol();
    await _load();
  }

  Future<void> _load() async {
    setState(() => _loading = true);
    try { final r = await ApiService.getMaquinarias(); setState(() { _items = r; _filtered = r; }); }
    catch (_) {} finally { setState(() => _loading = false); }
  }

  void _search(String q) {
    setState(() => _filtered = _items.where((m) =>
    m.nombre.toLowerCase().contains(q.toLowerCase()) ||
        m.codigo.toLowerCase().contains(q.toLowerCase())).toList());
  }

  void _showCambiarEstado(Maquinaria m) {
    showModalBottomSheet(context: context,
      shape: const RoundedRectangleBorder(borderRadius: BorderRadius.vertical(top: Radius.circular(20))),
      builder: (_) => Padding(
        padding: const EdgeInsets.all(24),
        child: Column(mainAxisSize: MainAxisSize.min, crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text('Cambiar estado', style: const TextStyle(fontSize: 17, fontWeight: FontWeight.w700)),
          Text(m.nombre, style: const TextStyle(color: kTextMuted, fontSize: 13)),
          const SizedBox(height: 16),
          ...['OPERATIVO','EN_REPARACION','FUERA_DE_SERVICIO','INACTIVO'].map((e) => ListTile(
            title: Text(Maquinaria(idMaquinaria:0,codigo:'',nombre:'',tipo:'',estado:e).estadoLabel,
                style: TextStyle(fontWeight: m.estado == e ? FontWeight.w700 : FontWeight.normal,
                    color: m.estado == e ? kPrimary : kText)),
            trailing: m.estado == e ? const Icon(Icons.check, color: kPrimary) : null,
            onTap: () async {
              Navigator.pop(context);
              await ApiService.cambiarEstadoMaquinaria(m.idMaquinaria, e);
              _load();
            },
          )),
        ]),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(children: [
      Padding(
        padding: const EdgeInsets.all(16),
        child: TextField(
          controller: _searchCtrl,
          onChanged: _search,
          decoration: const InputDecoration(
            hintText: 'Buscar por nombre o código...',
            prefixIcon: Icon(Icons.search, color: kTextMuted),
          ),
        ),
      ),
      Expanded(
        child: _loading ? const Center(child: CircularProgressIndicator())
            : _filtered.isEmpty ? const EmptyState(emoji: '⚙️', message: 'Sin maquinarias')
            : RefreshIndicator(
          onRefresh: _load,
          child: ListView.builder(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: _filtered.length,
            itemBuilder: (_, i) {
              final m = _filtered[i];
              final color = estadoBadgeColor(m.estado);
              return Card(
                child: ListTile(
                  contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                  leading: Container(
                    width: 44, height: 44,
                    decoration: BoxDecoration(color: kPrimaryLight, borderRadius: BorderRadius.circular(10)),
                    child: const Icon(Icons.precision_manufacturing, color: kPrimary, size: 20),
                  ),
                  title: Row(children: [
                    Expanded(child: Text(m.nombre, style: const TextStyle(fontWeight: FontWeight.w700, fontSize: 14))),
                    StatusBadge(label: m.estadoLabel, color: color),
                  ]),
                  subtitle: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                    const SizedBox(height: 4),
                    Text('${m.codigo} · ${m.tipo}', style: const TextStyle(fontSize: 12, color: kTextSec)),
                    if (m.ubicacion != null)
                      Text('📍 ${m.ubicacion}', style: const TextStyle(fontSize: 12, color: kTextMuted)),
                  ]),
                  trailing: _canChangeMaquinaEstado(_rol) ? IconButton(
                    icon: const Icon(Icons.swap_horiz, color: kPrimary),
                    tooltip: 'Cambiar estado',
                    onPressed: () => _showCambiarEstado(m),
                  ) : null,
                ),
              );
            },
          ),
        ),
      ),
    ]);
  }
}

// ════════════════════════════════════════════════════════════════════════════
// mantenimientos_screen.dart
// ════════════════════════════════════════════════════════════════════════════
class MantenimientosScreen extends StatefulWidget {
  const MantenimientosScreen({super.key});
  @override State<MantenimientosScreen> createState() => _MantenimientosScreenState();
}

class _MantenimientosScreenState extends State<MantenimientosScreen> {
  List<Mantenimiento> _items = [];
  bool _loading = true;
  String _filtro = 'ACTIVO';
  String _rol = '';

  @override void initState() { super.initState(); _loadAll(); }

  Future<void> _loadAll() async {
    _rol = await _getUserRol();
    await _load();
  }

  Future<void> _load() async {
    setState(() => _loading = true);
    try { final r = await ApiService.getMantenimientos(); setState(() => _items = r); }
    catch (_) {} finally { setState(() => _loading = false); }
  }

  Future<void> _finalizar(int id) async {
    if (!_canClose(_rol)) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('❌ No tienes permisos para finalizar mantenimientos'), backgroundColor: kDanger),
      );
      return;
    }
    final ok = await showDialog<bool>(context: context, builder: (_) => AlertDialog(
      title: const Text('Finalizar mantenimiento'),
      content: const Text('¿Marcar este mantenimiento como finalizado?'),
      actions: [
        TextButton(onPressed: () => Navigator.pop(context, false), child: const Text('Cancelar')),
        ElevatedButton(onPressed: () => Navigator.pop(context, true), child: const Text('Finalizar')),
      ],
    ));
    if (ok == true) {
      try {
        await ApiService.finalizarMantenimiento(id);
        _load();
        if (mounted) ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('✅ Mantenimiento finalizado'), backgroundColor: kSuccess),
        );
      } catch (e) {
        if (mounted) ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('❌ ${e.toString().replaceAll('Exception: ', '')}'), backgroundColor: kDanger),
        );
      }
    }
  }

  List<Mantenimiento> get _filtered =>
      _filtro.isEmpty ? _items : _items.where((m) => m.estado == _filtro).toList();

  @override
  Widget build(BuildContext context) {
    return Column(children: [
      SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.all(16),
        child: Row(children: [
          for (final f in [['','Todos'],['ACTIVO','Activos'],['FINALIZADO','Finalizados'],['CANCELADO','Cancelados']])
            Padding(
              padding: const EdgeInsets.only(right: 8),
              child: FilterChip(
                label: Text(f[1]),
                selected: _filtro == f[0],
                onSelected: (_) => setState(() => _filtro = f[0]),
                selectedColor: kPrimaryLight,
                labelStyle: TextStyle(color: _filtro == f[0] ? kPrimary : kTextSec, fontWeight: FontWeight.w600),
              ),
            ),
        ]),
      ),
      Expanded(
        child: _loading ? const Center(child: CircularProgressIndicator())
            : _filtered.isEmpty ? const EmptyState(emoji: '🔧', message: 'Sin mantenimientos')
            : RefreshIndicator(
          onRefresh: _load,
          child: ListView.builder(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: _filtered.length,
            itemBuilder: (_, i) {
              final m = _filtered[i];
              return Card(
                child: Padding(
                  padding: const EdgeInsets.all(14),
                  child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                    Row(children: [
                      Container(
                        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 3),
                        decoration: BoxDecoration(color: kPrimaryLight, borderRadius: BorderRadius.circular(6)),
                        child: Text('MTO-${m.idMantenimiento.toString().padLeft(3,'0')}',
                            style: const TextStyle(fontSize: 11, fontWeight: FontWeight.w700, color: kPrimary, fontFamily: 'monospace')),
                      ),
                      const SizedBox(width: 8),
                      StatusBadge(label: m.estado, color: estadoBadgeColor(m.estado)),
                      const Spacer(),
                      StatusBadge(label: m.tipo, color: kInfo),
                    ]),
                    const SizedBox(height: 8),
                    Text(m.descripcion ?? '—', style: const TextStyle(fontWeight: FontWeight.w600, fontSize: 14)),
                    const SizedBox(height: 4),
                    Text('Maq. #${m.idMaquinaria} · ${m.fecha ?? '—'}',
                        style: const TextStyle(fontSize: 12, color: kTextSec)),
                    if (m.observaciones != null) ...[
                      const SizedBox(height: 4),
                      Text('📝 ${m.observaciones}', style: const TextStyle(fontSize: 12, color: kTextMuted)),
                    ],
                    if (m.estado == 'ACTIVO') ...[
                      const SizedBox(height: 10),
                      SizedBox(
                        width: double.infinity,
                        child: ElevatedButton.icon(
                          onPressed: () => _finalizar(m.idMantenimiento),
                          icon: const Icon(Icons.check_circle_outline, size: 16),
                          label: const Text('Finalizar mantenimiento'),
                          style: ElevatedButton.styleFrom(backgroundColor: kSuccess),
                        ),
                      ),
                    ],
                  ]),
                ),
              );
            },
          ),
        ),
      ),
    ]);
  }
}

// ════════════════════════════════════════════════════════════════════════════
// fallas_screen.dart
// ════════════════════════════════════════════════════════════════════════════
class FallasScreen extends StatefulWidget {
  const FallasScreen({super.key});
  @override State<FallasScreen> createState() => _FallasScreenState();
}

class _FallasScreenState extends State<FallasScreen> {
  List<Falla> _items = [];
  List<Maquinaria> _maquinas = [];
  bool _loading = true;
  String _rol = '';

  @override void initState() { super.initState(); _loadAll(); }

  Future<void> _loadAll() async {
    _rol = await _getUserRol();
    await _load();
  }

  Future<void> _load() async {
    setState(() => _loading = true);
    try {
      final r = await Future.wait([
        ApiService.getFallas(),
        ApiService.getMaquinarias().catchError((_) => <Maquinaria>[]),
      ]);
      setState(() { _items = r[0] as List<Falla>; _maquinas = r[1] as List<Maquinaria>; });
    } catch (_) {} finally { setState(() => _loading = false); }
  }

  String _maqNombre(int? id) {
    if (id == null) return '—';
    final m = _maquinas.where((m) => m.idMaquinaria == id).firstOrNull;
    return m != null ? '${m.codigo} — ${m.nombre}' : 'Maq. #$id';
  }

  void _showReportarFalla() {
    final descCtrl = TextEditingController();
    final ubCtrl = TextEditingController();
    String gravedad = 'MEDIA';
    int? idMaq;

    showModalBottomSheet(
      context: context, isScrollControlled: true,
      shape: const RoundedRectangleBorder(borderRadius: BorderRadius.vertical(top: Radius.circular(20))),
      builder: (ctx) => StatefulBuilder(builder: (ctx, setS) => Padding(
        padding: EdgeInsets.only(left: 24, right: 24, top: 24, bottom: MediaQuery.of(ctx).viewInsets.bottom + 24),
        child: SingleChildScrollView(child: Column(mainAxisSize: MainAxisSize.min, crossAxisAlignment: CrossAxisAlignment.start, children: [
          const Text('Reportar Nueva Falla', style: TextStyle(fontSize: 17, fontWeight: FontWeight.w700)),
          const SizedBox(height: 20),
          const Text('MAQUINARIA', style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, letterSpacing: 0.5)),
          const SizedBox(height: 8),
          DropdownButtonFormField<int>(
            value: idMaq,
            hint: const Text('Seleccione maquinaria'),
            items: _maquinas.map((m) => DropdownMenuItem(value: m.idMaquinaria, child: Text('${m.codigo} — ${m.nombre}', overflow: TextOverflow.ellipsis))).toList(),
            onChanged: (v) => setS(() => idMaq = v),
            decoration: const InputDecoration(),
          ),
          const SizedBox(height: 14),
          const Text('GRAVEDAD', style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, letterSpacing: 0.5)),
          const SizedBox(height: 8),
          Wrap(spacing: 8, children: ['BAJA','MEDIA','ALTA','CRITICA'].map((g) =>
              ChoiceChip(label: Text(g), selected: gravedad == g,
                onSelected: (_) => setS(() => gravedad = g),
                selectedColor: kPrimaryLight,
                labelStyle: TextStyle(color: gravedad == g ? kPrimary : kTextSec, fontWeight: FontWeight.w600),
              )).toList()),
          const SizedBox(height: 14),
          const Text('DESCRIPCIÓN *', style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, letterSpacing: 0.5)),
          const SizedBox(height: 8),
          TextField(controller: descCtrl, maxLines: 3,
              decoration: const InputDecoration(hintText: 'Describe la falla...')),
          const SizedBox(height: 14),
          const Text('UBICACIÓN EXACTA', style: TextStyle(fontSize: 11, fontWeight: FontWeight.w700, letterSpacing: 0.5)),
          const SizedBox(height: 8),
          TextField(controller: ubCtrl, decoration: const InputDecoration(hintText: 'Ej. Motor principal, husillo...')),
          const SizedBox(height: 20),
          SizedBox(width: double.infinity, child: ElevatedButton(
            onPressed: () async {
              if (descCtrl.text.isEmpty || idMaq == null) {
                ScaffoldMessenger.of(ctx).showSnackBar(const SnackBar(content: Text('Completa los campos requeridos')));
                return;
              }
              await ApiService.reportarFalla(Falla(
                idFalla: 0, descripcion: descCtrl.text, gravedad: gravedad, activa: true,
                ubicacion: ubCtrl.text.isEmpty ? null : ubCtrl.text, idMaquinaria: idMaq,
                fecha: DateTime.now().toString().substring(0,10),
              ));
              if (mounted) { Navigator.pop(ctx); _load(); }
            },
            child: const Text('Guardar falla'),
          )),
        ])),
      )),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Stack(children: [
      _loading ? const Center(child: CircularProgressIndicator())
          : _items.isEmpty ? const EmptyState(emoji: '✅', message: 'Sin fallas registradas')
          : RefreshIndicator(
        onRefresh: _load,
        child: ListView.builder(
          padding: const EdgeInsets.fromLTRB(16,16,16,80),
          itemCount: _items.length,
          itemBuilder: (_, i) {
            final f = _items[i];
            return Card(child: Padding(
              padding: const EdgeInsets.all(14),
              child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                Row(children: [
                  Container(
                    padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 3),
                    decoration: BoxDecoration(color: kDangerBg, borderRadius: BorderRadius.circular(6)),
                    child: Text('FAL-${f.idFalla.toString().padLeft(3,'0')}',
                        style: const TextStyle(fontSize: 11, fontWeight: FontWeight.w700, color: kDanger, fontFamily: 'monospace')),
                  ),
                  const SizedBox(width: 8),
                  StatusBadge(label: f.gravedad, color: gravedadColor(f.gravedad)),
                  const Spacer(),
                  StatusBadge(label: f.activa ? 'Abierta' : 'Cerrada', color: f.activa ? kDanger : kSuccess),
                ]),
                const SizedBox(height: 8),
                Text(f.descripcion, style: const TextStyle(fontWeight: FontWeight.w600, fontSize: 14)),
                const SizedBox(height: 4),
                Text(_maqNombre(f.idMaquinaria), style: const TextStyle(fontSize: 12, color: kTextSec)),
                if (f.ubicacion != null)
                  Text('📍 ${f.ubicacion}', style: const TextStyle(fontSize: 12, color: kTextMuted)),
                if (f.activa) ...[
                  const SizedBox(height: 10),
                  SizedBox(width: double.infinity, child: OutlinedButton.icon(
                    onPressed: () async {
                      // Verificar permisos primero
                      if (!_canClose(_rol)) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                            content: Text('❌ No tienes permisos para cerrar fallas'),
                            backgroundColor: kDanger,
                          ),
                        );
                        return;
                      }
                      final ok = await showDialog<bool>(
                        context: context,
                        builder: (_) => AlertDialog(
                          title: const Text('Cerrar falla'),
                          content: const Text('¿Marcar esta falla como resuelta?'),
                          actions: [
                            TextButton(
                              onPressed: () => Navigator.pop(context, false),
                              child: const Text('Cancelar'),
                            ),
                            ElevatedButton(
                              onPressed: () => Navigator.pop(context, true),
                              style: ElevatedButton.styleFrom(backgroundColor: kSuccess),
                              child: const Text('Cerrar falla'),
                            ),
                          ],
                        ),
                      );
                      if (ok == true) {
                        try {
                          await ApiService.cerrarFalla(f.idFalla);
                          _load();
                          if (mounted) {
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(
                                content: Text('✅ Falla cerrada correctamente'),
                                backgroundColor: kSuccess,
                              ),
                            );
                          }
                        } catch (e) {
                          if (mounted) {
                            ScaffoldMessenger.of(context).showSnackBar(
                              SnackBar(
                                content: Text('❌ ${e.toString().replaceAll('Exception: ', '')}'),
                                backgroundColor: kDanger,
                              ),
                            );
                          }
                        }
                      }
                    },
                    icon: const Icon(Icons.check, size: 16, color: kSuccess),
                    label: const Text('Marcar como resuelta', style: TextStyle(color: kSuccess)),
                    style: OutlinedButton.styleFrom(side: const BorderSide(color: kSuccess)),
                  )),
                ],
              ]),
            ));
          },
        ),
      ),
      if (_canWrite(_rol)) Positioned(
        bottom: 16, right: 16,
        child: FloatingActionButton.extended(
          onPressed: _showReportarFalla,
          backgroundColor: kPrimary,
          icon: const Icon(Icons.add, color: Colors.white),
          label: const Text('Reportar Falla', style: TextStyle(color: Colors.white, fontWeight: FontWeight.w700)),
        ),
      ),
    ]);
  }
}

// ════════════════════════════════════════════════════════════════════════════
// repuestos_screen.dart
// ════════════════════════════════════════════════════════════════════════════
class RepuestosScreen extends StatefulWidget {
  const RepuestosScreen({super.key});
  @override State<RepuestosScreen> createState() => _RepuestosScreenState();
}

class _RepuestosScreenState extends State<RepuestosScreen> {
  List<Repuesto> _items = [];
  List<Repuesto> _filtered = [];
  bool _loading = true;
  final _searchCtrl = TextEditingController();

  @override void initState() { super.initState(); _load(); }

  Future<void> _load() async {
    setState(() => _loading = true);
    try { final r = await ApiService.getRepuestos(); setState(() { _items = r; _filtered = r; }); }
    catch (_) {} finally { setState(() => _loading = false); }
  }

  void _search(String q) {
    setState(() => _filtered = _items.where((r) =>
    r.nombre.toLowerCase().contains(q.toLowerCase()) ||
        r.codigo.toLowerCase().contains(q.toLowerCase())).toList());
  }

  @override
  Widget build(BuildContext context) {
    return Column(children: [
      Padding(
        padding: const EdgeInsets.all(16),
        child: TextField(
          controller: _searchCtrl, onChanged: _search,
          decoration: const InputDecoration(hintText: 'Buscar repuesto...', prefixIcon: Icon(Icons.search, color: kTextMuted)),
        ),
      ),
      Expanded(
        child: _loading ? const Center(child: CircularProgressIndicator())
            : _filtered.isEmpty ? const EmptyState(emoji: '📦', message: 'Sin repuestos')
            : RefreshIndicator(
          onRefresh: _load,
          child: ListView.builder(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: _filtered.length,
            itemBuilder: (_, i) {
              final r = _filtered[i];
              final color = r.sinStock ? kDanger : r.stockBajo ? kWarning : kSuccess;
              return Card(child: ListTile(
                leading: Container(
                  width: 44, height: 44,
                  decoration: BoxDecoration(color: color.withOpacity(0.1), borderRadius: BorderRadius.circular(10)),
                  child: Icon(Icons.inventory_2, color: color, size: 20),
                ),
                title: Row(children: [
                  Expanded(child: Text(r.nombre, style: const TextStyle(fontWeight: FontWeight.w700, fontSize: 14))),
                  StatusBadge(label: r.estadoLabel, color: color),
                ]),
                subtitle: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                  const SizedBox(height: 4),
                  Text('${r.codigo}${r.categoria != null ? ' · ${r.categoria}' : ''}',
                      style: const TextStyle(fontSize: 12, color: kTextSec)),
                  Text('Stock: ${r.stockDisponible} ${r.unidad ?? 'unidad'} (mín: ${r.stockMinimo})',
                      style: TextStyle(fontSize: 12, color: r.stockBajo ? kDanger : kTextMuted, fontWeight: r.stockBajo ? FontWeight.w700 : FontWeight.normal)),
                ]),
              ));
            },
          ),
        ),
      ),
    ]);
  }
}

// ════════════════════════════════════════════════════════════════════════════
// alertas_screen.dart
// ════════════════════════════════════════════════════════════════════════════
class AlertasScreen extends StatefulWidget {
  const AlertasScreen({super.key});
  @override State<AlertasScreen> createState() => _AlertasScreenState();
}

class _AlertasScreenState extends State<AlertasScreen> {
  List<Alerta> _items = [];
  bool _loading = true;

  @override void initState() { super.initState(); _load(); }

  Future<void> _load() async {
    setState(() => _loading = true);
    try { final r = await ApiService.getAlertas(); setState(() => _items = r); }
    catch (_) {} finally { setState(() => _loading = false); }
  }

  @override
  Widget build(BuildContext context) {
    final activas   = _items.where((a) => a.activa).toList();
    final inactivas = _items.where((a) => !a.activa).take(5).toList();

    return _loading ? const Center(child: CircularProgressIndicator())
        : _items.isEmpty ? const EmptyState(emoji: '🔔', message: 'Sin alertas')
        : RefreshIndicator(
      onRefresh: _load,
      child: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          if (activas.isNotEmpty) ...[
            Text('🔴 Alertas activas (${activas.length})',
                style: const TextStyle(fontWeight: FontWeight.w700, fontSize: 14, color: kText)),
            const SizedBox(height: 10),
            ...activas.map((a) => Container(
              margin: const EdgeInsets.only(bottom: 10),
              padding: const EdgeInsets.all(14),
              decoration: BoxDecoration(
                color: a.leida ? kCard : const Color(0xFFFFFBEA),
                borderRadius: BorderRadius.circular(12),
                border: Border.all(color: a.leida ? kBorder : const Color(0xFFFDE68A)),
              ),
              child: Row(crossAxisAlignment: CrossAxisAlignment.start, children: [
                Text(a.emoji, style: const TextStyle(fontSize: 22)),
                const SizedBox(width: 12),
                Expanded(child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                  Text(a.mensaje, style: const TextStyle(fontSize: 13, color: kText)),
                  if (!a.leida) ...[
                    const SizedBox(height: 4),
                    const Text('No leída', style: TextStyle(fontSize: 11, color: kDanger, fontWeight: FontWeight.w700)),
                  ],
                ])),
                IconButton(
                  icon: const Icon(Icons.close, size: 18, color: kTextMuted),
                  onPressed: () async { await ApiService.desactivarAlerta(a.idAlerta); _load(); },
                  tooltip: 'Desactivar',
                ),
              ]),
            )),
          ],
          if (inactivas.isNotEmpty) ...[
            const SizedBox(height: 8),
            Text('✅ Resueltas (${inactivas.length})',
                style: const TextStyle(fontWeight: FontWeight.w700, fontSize: 14, color: kTextSec)),
            const SizedBox(height: 10),
            ...inactivas.map((a) => Opacity(opacity: 0.5, child: Container(
              margin: const EdgeInsets.only(bottom: 8),
              padding: const EdgeInsets.all(12),
              decoration: BoxDecoration(color: kCard, borderRadius: BorderRadius.circular(12), border: Border.all(color: kBorder)),
              child: Row(children: [
                Text(a.emoji, style: const TextStyle(fontSize: 18)),
                const SizedBox(width: 10),
                Expanded(child: Text(a.mensaje, style: const TextStyle(fontSize: 12, color: kTextSec), maxLines: 2)),
              ]),
            ))),
          ],
        ],
      ),
    );
  }
}