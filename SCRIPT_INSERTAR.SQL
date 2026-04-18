-- ROLES
INSERT INTO ROLES VALUES (1, 'Administrador');
INSERT INTO ROLES VALUES (2, 'Técnico');
INSERT INTO ROLES VALUES (3, 'Supervisor');
INSERT INTO ROLES VALUES (4, 'Operario');
INSERT INTO ROLES VALUES (5, 'Auditor');

-- USUARIOS
INSERT INTO USUARIOS VALUES (1, 'Juan Carlos', 'Calle 12 #45', '3101234567');
INSERT INTO USUARIOS VALUES (2, 'Selena Gomez', 'Carrera 8 #23', '3209876543');
INSERT INTO USUARIOS VALUES (3, 'Sergio Lancheros', 'Av. Central 5', '3154567890');
INSERT INTO USUARIOS VALUES (4, 'Camila Arenas', 'Calle 30 #10', '305678572');
INSERT INTO USUARIOS VALUES (5, 'Sebastiam arias', 'Carrera 15 #7', '3187654321');

-- ADMINISTRADORES
INSERT INTO ADMINISTRADORES VALUES (1, 'Ana Torres', 'Calle 50 #20', '3104072234');
INSERT INTO ADMINISTRADORES VALUES (2, 'Luis Mora', 'Carrera 22 #9', '3053925789');
INSERT INTO ADMINISTRADORES VALUES (3, 'Diana Cruz', 'Av. Norte 14', '3178902344');


-- TECNICOS
INSERT INTO TECNICOS VALUES (1, 'Héctor Gil', 'Eléctrica',    '3160000001');
INSERT INTO TECNICOS VALUES (2, 'Paola Ossa', 'Mecánica',     '3160000002');
INSERT INTO TECNICOS VALUES (3, 'Camilo Rey', 'Hidráulica',   '3160000003');
INSERT INTO TECNICOS VALUES (4, 'Nora Pinto', 'Electrónica',  '3160000004');
INSERT INTO TECNICOS VALUES (5, 'Iván Lara',  'Neumática',    '3160000005');

-- MAQUINARIAS
INSERT INTO MAQUINARIAS VALUES (1, 'Torno CNC',      '2024-01-10', 'Torno de control numérico', 'Activo');
INSERT INTO MAQUINARIAS VALUES (2, 'Fresadora',       '2023-06-15', 'Fresadora industrial',      'Activo');
INSERT INTO MAQUINARIAS VALUES (3, 'Compresor',       '2022-11-20', 'Compresor de aire 50L',     'En mantenimiento');
INSERT INTO MAQUINARIAS VALUES (4, 'Soldadora MIG',   '2024-03-05', 'Soldadora semiautomática',  'Activo');
INSERT INTO MAQUINARIAS VALUES (5, 'Taladro Radial',  '2023-09-01', 'Taladro de columna',        'Inactivo');

-- FALLAS
INSERT INTO FALLAS VALUES (1, '2024-02-01', 'Sobrecalentamiento motor',  'Alta');
INSERT INTO FALLAS VALUES (2, '2024-03-12', 'Fuga de aceite hidráulico', 'Media');
INSERT INTO FALLAS VALUES (3, '2024-04-05', 'Ruido en rodamiento',       'Baja');
INSERT INTO FALLAS VALUES (4, '2024-05-18', 'Fallo en sensor de presión','Alta');
INSERT INTO FALLAS VALUES (5, '2024-06-22', 'Corte en correa de transmisión', 'Media');

-- MANTENIMIENTOS
INSERT INTO MANTENIMIENTOS VALUES (1, 'Preventivo', '2024-01-15', 'Cambio de aceite y filtros',    'Completado');
INSERT INTO MANTENIMIENTOS VALUES (2, 'Correctivo', '2024-02-20', 'Reparación de motor eléctrico', 'Completado');
INSERT INTO MANTENIMIENTOS VALUES (3, 'Preventivo', '2024-03-10', 'Revisión sistema hidráulico',   'En proceso');
INSERT INTO MANTENIMIENTOS VALUES (4, 'Predictivo', '2024-04-25', 'Análisis de vibraciones',       'Completado');
INSERT INTO MANTENIMIENTOS VALUES (5, 'Correctivo', '2024-05-30', 'Cambio de rodamientos',         'Pendiente');

-- REPORTES
INSERT INTO REPORTES VALUES (1, '2024-02-02', 'Falla',        'Reporte por sobrecalentamiento', 'Cerrado');
INSERT INTO REPORTES VALUES (2, '2024-03-13', 'Mantenimiento','Seguimiento mantenimiento 2',    'Abierto');
INSERT INTO REPORTES VALUES (3, '2024-04-06', 'Inspección',   'Inspección rutinaria mensual',   'Cerrado');
INSERT INTO REPORTES VALUES (4, '2024-05-19', 'Falla',        'Sensor de presión dañado',       'En revisión');
INSERT INTO REPORTES VALUES (5, '2024-06-23', 'Mantenimiento','Cambio de correa programado',    'Abierto');

-- INVENTARIOS
INSERT INTO INVENTARIOS VALUES (1, 'Bodega Norte',   'Calle 80 #15',   '60530918934');
INSERT INTO INVENTARIOS VALUES (2, 'Bodega Sur',     'Carrera 5 #90',  '60531049589');
INSERT INTO INVENTARIOS VALUES (3, 'Almacén Central','Av. Industrial 1','6053043945');
