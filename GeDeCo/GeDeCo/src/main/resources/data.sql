----------------------------------------------------
-- 1. COMPETICIONES
----------------------------------------------------

-- 1. Competición para Asignación COMPLETA (4 Equipos)
INSERT INTO competiciones (id, nombre, deporte, fecha_inicio, fecha_fin, num_pistas, dias_reservados) VALUES
(101, 'Liga de Verano de Fútbol 7', 'Fútbol', '2025-10-20', '2025-10-27', 2, 'LUNES,MIERCOLES'); 

-- 2. Competición de Baloncesto (Ejemplo original, se usa para probar que otros IDs no interfieran)
INSERT INTO competiciones (id, nombre, deporte, fecha_inicio, fecha_fin, num_pistas, dias_reservados) VALUES
(102, 'Copa de Invierno de Baloncesto', 'Baloncesto', '2025-11-15', '2025-12-20', 1, 'SABADO');

-- 3. Competición para Asignación PARCIAL (5 Equipos)
INSERT INTO competiciones (id, nombre, deporte, fecha_inicio, fecha_fin, num_pistas, dias_reservados) VALUES
(103, 'Torneo de Otoño de Voleibol', 'Voleibol', '2025-11-01', '2025-11-30', 2, 'LUNES,MARTES'); 


----------------------------------------------------
-- 2. EQUIPOS
----------------------------------------------------

-- Equipos para Competición 101 (4 equipos = 2 partidos)
INSERT INTO equipos (id, nombre) VALUES
(201, 'Los Invencibles'), 
(202, 'Rayo Veloz F.C.'), 
(205, 'Estrella Roja F.C.'),
(206, 'Defensores Murcianos');

-- Equipos para Competición 103 (5 equipos = 2 partidos + 1 no asignado)
INSERT INTO equipos (id, nombre) VALUES
(207, 'Tigres del Campo'),
(208, 'Águilas de Acero'),
(209, 'Huracanes F.C.'),
(210, 'Gatos Salvajes'),
(211, 'Torbellinos A.C.');

-- Equipos adicionales para otras competiciones
INSERT INTO equipos (id, nombre) VALUES
(204, 'Net Seekers'); 


----------------------------------------------------
-- 3. EQUIPOS EN COMPETICIONES (Tabla de Unión)
----------------------------------------------------

-- Competición 101: 4 equipos (Para prueba de asignación completa)
INSERT INTO competicion_equipos (competicion_id, equipo_id) VALUES
(101, 201), (101, 202), (101, 205), (101, 206);

-- Competición 103: 5 equipos (Para prueba de asignación parcial)
INSERT INTO competicion_equipos (competicion_id, equipo_id) VALUES
(103, 207), (103, 208), (103, 209), (103, 210), (103, 211);
