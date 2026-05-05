-- ================================================================
-- Script de inicialización para la Base de Datos
-- Crear la base de datos y los datos iniciales
-- ================================================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS grupo_familiar DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE grupo_familiar;

-- ================================================================
-- 1. ROLES (Datos iniciales)
-- ================================================================

INSERT IGNORE INTO rol (id, nombre, descripcion) VALUES
(1, 'ADMIN', 'Administrador del sistema'),
(2, 'SUP_GENERAL', 'Supervisor General'),
(3, 'SUP_SECTORIAL', 'Supervisor Sectorial'),
(4, 'LIDER', 'Líder de grupo'),
(5, 'TESORERO', 'Tesorero');

-- ================================================================
-- 2. SECTORES (Datos iniciales)
-- ================================================================
INSERT IGNORE INTO sector (id, nombre, descripcion, activo) VALUES
(1, 'Sector Central', 'Sector que abarca la zona central', TRUE),
(2, 'Sector Norte', 'Sector que abarca la zona norte', TRUE);

-- ================================================================
-- 3. USUARIOS (Datos de prueba)
-- ================================================================

-- Credenciales comunes:
-- admin@grupofamiliar.com / admin123
-- supervisor@grupofamiliar.com / lider123 (usando la contraseña temporal del lider para probar)
-- lider@grupofamiliar.com / lider123

-- (3, 'Maria', 'Supervisor', 'supervisor@grupofamiliar.com', '5555555555', ...) 
-- Aquí el lider pertenece a un id_sector (agregamos el campo id_sector, aunque como la foreign key podría insertarse después o requerir id_sector=null por ahora)

INSERT IGNORE INTO usuario (id, nombres, apellidos, email, telefono, password_hash, id_rol, id_sector, id_supervisor, activo, creado_en, actualizado_en) VALUES
-- Admin (Sin supervisor, sin sector)
(1, 'Luis', 'Administrador', 'admin@grupofamiliar.com', '1234567890', '$2a$10$slYQmyNdGzin7olVN3p5aOK0GWZoDar.0Vs5qTwmHJWj4.D0VgVa6', 1, NULL, NULL, TRUE, NOW(), NOW()),

-- Supervisor Sectorial (id_rol = 3, id_sector = 1)
(2, 'Maria', 'Supervisor', 'supervisor@grupofamiliar.com', '5555555555', '$2a$10$1xR5Zm0D.H0k5Zz1pK8L3OgWvN7mQ2aB5tC3dE4fP9gH1iJ2kL3nM', 3, 1, NULL, TRUE, NOW(), NOW()),

-- Líder (id_rol = 4, id_sector = 1, id_supervisor = 2)
(3, 'Juan', 'Líder', 'lider@grupofamiliar.com', '9876543210', '$2a$10$1xR5Zm0D.H0k5Zz1pK8L3OgWvN7mQ2aB5tC3dE4fP9gH1iJ2kL3nM', 4, 1, 2, TRUE, NOW(), NOW());

-- ================================================================
-- 4. TIPOS DE MIEMBRO (Datos iniciales)
-- ================================================================

INSERT IGNORE INTO tipo_miembro (id, nombre) VALUES
(1, 'HERMANO'),
(2, 'AMIGO'),
(3, 'ADOLESCENTE'),
(4, 'NIÑO_CRISTIANO'),
(5, 'NIÑO_AMIGO');

-- ================================================================
-- 5. CATEGORÍAS FINANCIERAS (Datos iniciales)
-- ================================================================

INSERT IGNORE INTO categoria_financiera (id, nombre, tipo, activo) VALUES
(1, 'Ofrenda Sábado', 'INGRESO', TRUE),
(2, 'Ofrenda Miércoles', 'INGRESO', TRUE),
(3, 'Ofrenda Niños', 'INGRESO', TRUE),
(4, 'Otras Ofrendas', 'INGRESO', TRUE),
(5, 'Gasto Operativo', 'EGRESO', TRUE),
(6, 'Gasto de Materiales', 'EGRESO', TRUE),
(7, 'Transporte', 'EGRESO', TRUE),
(8, 'Alimentos', 'EGRESO', TRUE);

-- ================================================================
-- NOTIFICACIÓN: Asegúrate de cambiar la contraseña del admin en producción
-- ================================================================
-- Admin credentials (cambiar después):
-- Email: admin@grupofamiliar.com
-- Password: admin123
-- Role: ADMIN
--
-- Lider credentials (para pruebas):
-- Email: lider@grupofamiliar.com
-- Password: lider123
-- Role: LIDER
--
-- Supervisor Sectorial credentials:
-- Email: supervisor@grupofamiliar.com
-- Password: lider123
-- Role: SUP_SECTORIAL
