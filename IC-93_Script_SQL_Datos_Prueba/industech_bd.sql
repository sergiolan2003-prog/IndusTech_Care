-- ============================================================
--  IndusTech BD — Script completo de creación
--  Ejecutar en MySQL / MariaDB antes de iniciar la aplicación
-- ============================================================

CREATE DATABASE IF NOT EXISTS industech_bd
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE industech_bd;

-- ── 1. USUARIO ────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario  INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    direccion   VARCHAR(255) DEFAULT '',
    telefono    VARCHAR(20)  DEFAULT '',
    rol         VARCHAR(30)  NOT NULL DEFAULT 'CONSULTOR',
    contrasena  VARCHAR(255) NOT NULL DEFAULT '1234',
    CONSTRAINT chk_rol CHECK (rol IN ('ADMIN','JEFE_MANTENIMIENTO','TECNICO','CONSULTOR'))
);

-- ── 2. MAQUINARIA ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS maquinaria (
    id_maquinaria INT AUTO_INCREMENT PRIMARY KEY,
    tipo          VARCHAR(100) NOT NULL,
    fecha         DATE         NOT NULL,
    descripcion   TEXT,
    estado        VARCHAR(50)  NOT NULL DEFAULT 'OPERATIVO',
    CONSTRAINT chk_estado_maq CHECK (estado IN ('OPERATIVO','EN_REPARACION','FUERA_DE_SERVICIO'))
);

-- ── 3. TECNICO ────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS tecnico (
    id_tecnico   INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(150) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    telefono     VARCHAR(20)  NOT NULL
);

-- ── 4. REPUESTO ───────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS repuesto (
    id_repuesto      INT AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(150)  NOT NULL,
    referencia       VARCHAR(100)  NOT NULL UNIQUE,
    unidad           VARCHAR(50)   NOT NULL DEFAULT 'unidad',
    precio_unitario  DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    stock_disponible INT           NOT NULL DEFAULT 0
);

-- ── 5. FALLA ──────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS falla (
    id_falla    INT AUTO_INCREMENT PRIMARY KEY,
    fecha       DATE        NOT NULL,
    descripcion TEXT        NOT NULL,
    gravedad    VARCHAR(10) NOT NULL DEFAULT 'MEDIA',
    CONSTRAINT chk_gravedad CHECK (gravedad IN ('BAJA','MEDIA','ALTA','CRITICA'))
);

-- ── 6. MANTENIMIENTO ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS mantenimiento (
    id_mantenimiento INT AUTO_INCREMENT PRIMARY KEY,
    tipo             VARCHAR(50)   NOT NULL DEFAULT 'Preventivo',
    fecha            DATE          NOT NULL,
    descripcion      TEXT,
    estado           VARCHAR(20)   NOT NULL DEFAULT 'ACTIVO',
    hora_inicio      TIME          DEFAULT NULL,
    hora_fin         TIME          DEFAULT NULL,
    costo_mano_obra  DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    costo_repuestos  DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    id_maquinaria    INT           NOT NULL,
    CONSTRAINT fk_mant_maq FOREIGN KEY (id_maquinaria)
        REFERENCES maquinaria(id_maquinaria) ON DELETE CASCADE,
    CONSTRAINT chk_estado_mant CHECK (estado IN ('ACTIVO','FINALIZADO','CANCELADO'))
);

-- ── 7. MANTENIMIENTO_TECNICO (relación N:M) ───────────────────
CREATE TABLE IF NOT EXISTS mantenimiento_tecnico (
    id_mantenimiento INT NOT NULL,
    id_tecnico       INT NOT NULL,
    PRIMARY KEY (id_mantenimiento, id_tecnico),
    CONSTRAINT fk_mt_mant FOREIGN KEY (id_mantenimiento)
        REFERENCES mantenimiento(id_mantenimiento) ON DELETE CASCADE,
    CONSTRAINT fk_mt_tec  FOREIGN KEY (id_tecnico)
        REFERENCES tecnico(id_tecnico) ON DELETE CASCADE
);

-- ── 8. MANTENIMIENTO_REPUESTO (relación N:M con cantidad) ─────
CREATE TABLE IF NOT EXISTS mantenimiento_repuesto (
    id_mantenimiento INT NOT NULL,
    id_repuesto      INT NOT NULL,
    cantidad         INT NOT NULL DEFAULT 1,
    PRIMARY KEY (id_mantenimiento, id_repuesto),
    CONSTRAINT fk_mr_mant FOREIGN KEY (id_mantenimiento)
        REFERENCES mantenimiento(id_mantenimiento) ON DELETE CASCADE,
    CONSTRAINT fk_mr_rep  FOREIGN KEY (id_repuesto)
        REFERENCES repuesto(id_repuesto) ON DELETE CASCADE
);

-- ── 9. REPORTE ────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS reporte (
    id_reporte  INT AUTO_INCREMENT PRIMARY KEY,
    fecha       DATE        NOT NULL,
    tipo        VARCHAR(50) NOT NULL DEFAULT 'Falla',
    descripcion TEXT,
    estado      VARCHAR(20) NOT NULL DEFAULT 'ABIERTO',
    CONSTRAINT chk_estado_rep CHECK (estado IN ('ABIERTO','CERRADO'))
);

-- ── 10. ALERTA ────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS alerta (
    id_alerta   INT AUTO_INCREMENT PRIMARY KEY,
    mensaje     TEXT        NOT NULL,
    tipo        VARCHAR(20) NOT NULL DEFAULT 'FALLA',
    activa      TINYINT(1)  NOT NULL DEFAULT 1,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_tipo_alerta CHECK (tipo IN ('FALLA','MANTENIMIENTO','STOCK'))
);

-- ── DATOS INICIALES ───────────────────────────────────────────
-- Usuario administrador por defecto  (login: id=1, nombre=admin, contrasena=admin123)
INSERT INTO usuario (nombre, direccion, telefono, rol, contrasena)
VALUES ('admin', 'Oficina Central', '3001234567', 'ADMIN', 'admin123')
ON DUPLICATE KEY UPDATE nombre = nombre;

-- Maquinaria de ejemplo
INSERT INTO maquinaria (tipo, fecha, descripcion, estado) VALUES
  ('Torno CNC',         CURDATE(), 'Torno de control numérico para piezas de precisión', 'OPERATIVO'),
  ('Fresadora',         CURDATE(), 'Fresadora vertical de alta velocidad',                'OPERATIVO'),
  ('Compresora',        CURDATE(), 'Compresora de aire industrial 500 PSI',               'EN_REPARACION'),
  ('Soldadora MIG',     CURDATE(), 'Soldadora MIG/MAG semi-automática',                  'OPERATIVO'),
  ('Taladro Industrial',CURDATE(), 'Taladro de columna 2HP',                              'FUERA_DE_SERVICIO')
ON DUPLICATE KEY UPDATE tipo = tipo;

-- Técnicos de ejemplo
INSERT INTO tecnico (nombre, especialidad, telefono) VALUES
  ('Carlos Pérez',    'Mecánica',   '3111234567'),
  ('Andrés López',    'Eléctrica',  '3122345678'),
  ('Juliana Torres',  'Hidráulica', '3133456789')
ON DUPLICATE KEY UPDATE nombre = nombre;
