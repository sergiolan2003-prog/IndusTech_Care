// ── Maquinaria ────────────────────────────────────────────────────────────────
class Maquinaria {
  final int    idMaquinaria;
  final String codigo;
  final String nombre;
  final String tipo;
  final String estado;
  final String? marca;
  final String? modelo;
  final String? ubicacion;
  final String? descripcion;

  Maquinaria({
    required this.idMaquinaria,
    required this.codigo,
    required this.nombre,
    required this.tipo,
    required this.estado,
    this.marca, this.modelo, this.ubicacion, this.descripcion,
  });

  factory Maquinaria.fromJson(Map<String, dynamic> j) => Maquinaria(
    idMaquinaria: j['idMaquinaria'] ?? j['id'] ?? 0,
    codigo:       j['codigo']       ?? '',
    nombre:       j['nombre']       ?? '',
    tipo:         j['tipo']         ?? '',
    estado:       j['estado']       ?? '',
    marca:        j['marca'],
    modelo:       j['modelo'],
    ubicacion:    j['ubicacion'],
    descripcion:  j['descripcion'],
  );

  String get estadoLabel {
    switch (estado) {
      case 'OPERATIVO':          return 'Operativo';
      case 'EN_REPARACION':      return 'En reparación';
      case 'FUERA_DE_SERVICIO':  return 'Fuera de servicio';
      case 'INACTIVO':           return 'Inactivo';
      default:                   return estado;
    }
  }
}

// ── Mantenimiento ─────────────────────────────────────────────────────────────
class Mantenimiento {
  final int    idMantenimiento;
  final String tipo;
  final String estado;
  final String? descripcion;
  final String? observaciones;
  final String? fecha;
  final int?   idMaquinaria;
  final int?   idTecnicoPrincipal;

  Mantenimiento({
    required this.idMantenimiento,
    required this.tipo,
    required this.estado,
    this.descripcion, this.observaciones,
    this.fecha, this.idMaquinaria, this.idTecnicoPrincipal,
  });

  factory Mantenimiento.fromJson(Map<String, dynamic> j) => Mantenimiento(
    idMantenimiento:    j['idMantenimiento'] ?? j['id'] ?? 0,
    tipo:               j['tipo']            ?? '',
    estado:             j['estado']          ?? '',
    descripcion:        j['descripcion'],
    observaciones:      j['observaciones'],
    fecha:              j['fecha'],
    idMaquinaria:       j['idMaquinaria'],
    idTecnicoPrincipal: j['idTecnicoPrincipal'],
  );

  Map<String, dynamic> toJson() => {
    'tipo':               tipo,
    'estado':             estado,
    'descripcion':        descripcion,
    'observaciones':      observaciones,
    'fecha':              fecha,
    'idMaquinaria':       idMaquinaria,
    'idTecnicoPrincipal': idTecnicoPrincipal,
  };
}

// ── Falla ─────────────────────────────────────────────────────────────────────
class Falla {
  final int    idFalla;
  final String descripcion;
  final String gravedad;
  final bool   activa;
  final String? ubicacion;
  final String? fecha;
  final int?   idMaquinaria;

  Falla({
    required this.idFalla,
    required this.descripcion,
    required this.gravedad,
    required this.activa,
    this.ubicacion, this.fecha, this.idMaquinaria,
  });

  factory Falla.fromJson(Map<String, dynamic> j) => Falla(
    idFalla:      j['idFalla']     ?? j['id'] ?? 0,
    descripcion:  j['descripcion'] ?? '',
    gravedad:     j['gravedad']    ?? 'MEDIA',
    activa:       j['activa']      ?? true,
    ubicacion:    j['ubicacion'],
    fecha:        j['fecha'],
    idMaquinaria: j['idMaquinaria'],
  );

  Map<String, dynamic> toJson() => {
    'descripcion':  descripcion,
    'gravedad':     gravedad,
    'activa':       activa,
    'ubicacion':    ubicacion,
    'fecha':        fecha,
    'idMaquinaria': idMaquinaria,
  };
}

// ── Repuesto ──────────────────────────────────────────────────────────────────
class Repuesto {
  final int    idRepuesto;
  final String codigo;
  final String nombre;
  final int    stockDisponible;
  final int    stockMinimo;
  final String? categoria;
  final String? marca;
  final String? unidad;

  Repuesto({
    required this.idRepuesto,
    required this.codigo,
    required this.nombre,
    required this.stockDisponible,
    required this.stockMinimo,
    this.categoria, this.marca, this.unidad,
  });

  factory Repuesto.fromJson(Map<String, dynamic> j) => Repuesto(
    idRepuesto:      j['idRepuesto']      ?? j['id'] ?? 0,
    codigo:          j['codigo']          ?? '',
    nombre:          j['nombre']          ?? '',
    stockDisponible: j['stockDisponible'] ?? j['stock'] ?? 0,
    stockMinimo:     j['stockMinimo']     ?? 0,
    categoria:       j['categoria'],
    marca:           j['marca'],
    unidad:          j['unidad'],
  );

  bool get stockBajo => stockDisponible <= stockMinimo;
  bool get sinStock  => stockDisponible == 0;

  String get estadoLabel {
    if (sinStock)  return 'Sin stock';
    if (stockBajo) return 'Stock bajo';
    return 'Disponible';
  }
}

// ── Alerta ────────────────────────────────────────────────────────────────────
class Alerta {
  final int    idAlerta;
  final String mensaje;
  final String tipo;
  final bool   activa;
  final bool   leida;
  final String? fechaCreacion;

  Alerta({
    required this.idAlerta,
    required this.mensaje,
    required this.tipo,
    required this.activa,
    required this.leida,
    this.fechaCreacion,
  });

  factory Alerta.fromJson(Map<String, dynamic> j) => Alerta(
    idAlerta:      j['idAlerta']      ?? j['id'] ?? 0,
    mensaje:       j['mensaje']       ?? '',
    tipo:          j['tipo']          ?? '',
    activa:        j['activa']        ?? true,
    leida:         j['leida']         ?? false,
    fechaCreacion: j['fechaCreacion'],
  );

  String get emoji {
    switch (tipo) {
      case 'FALLA':         return '⚠️';
      case 'MANTENIMIENTO': return '🔧';
      case 'STOCK':         return '📦';
      default:              return '🔔';
    }
  }
}