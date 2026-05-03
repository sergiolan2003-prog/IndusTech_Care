class Usuario {
  final int idUsuario;
  final String nombre;
  final String rol;
  final String token;
  final String? especialidad;
  final String? telefono;
  final String? direccion;

  Usuario({
    required this.idUsuario,
    required this.nombre,
    required this.rol,
    required this.token,
    this.especialidad,
    this.telefono,
    this.direccion,
  });

  factory Usuario.fromJson(Map<String, dynamic> json) {
    return Usuario(
      idUsuario:   json['id']        ?? json['idUsuario'] ?? 0,
      nombre:      json['nombre']    ?? '',
      rol:         json['rol']       ?? '',
      token:       json['token']     ?? '',
      especialidad:json['especialidad'],
      telefono:    json['telefono'],
      direccion:   json['direccion'],
    );
  }

  String get initials {
    final parts = nombre.split(' ');
    if (parts.length >= 2) return '${parts[0][0]}${parts[1][0]}'.toUpperCase();
    return nombre.isNotEmpty ? nombre[0].toUpperCase() : 'U';
  }

  String get rolLabel {
    switch (rol) {
      case 'TECNICO':           return 'Técnico';
      case 'OPERARIO':          return 'Operario';
      case 'CONSULTOR':         return 'Consultor';
      case 'JEFE_MANTENIMIENTO':return 'Jefe de Mantenimiento';
      case 'ADMIN':             return 'Administrador';
      default:                  return rol;
    }
  }
}