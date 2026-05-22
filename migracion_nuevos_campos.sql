-- =============================================================
-- SCRIPT DE MIGRACIÓN - Nuevos campos en tabla `reporte`
-- Fecha: 2026-05-21
-- =============================================================
-- EJECUTAR ESTE SCRIPT EN TU BASE DE DATOS ANTES DE REINICIAR
-- EL BACKEND CON LOS NUEVOS CAMBIOS.
-- =============================================================

-- 1. Renombrar cant_hr_oracion → culto_horacion
--    (MySQL 8.0+)
ALTER TABLE reporte RENAME COLUMN cant_hr_oracion TO culto_horacion;

-- Si usas MySQL < 8.0, usa la siguiente sintaxis en su lugar:
-- ALTER TABLE reporte CHANGE cant_hr_oracion culto_horacion SMALLINT NOT NULL DEFAULT 0;

-- 2. Eliminar columna tiempo_oracion
ALTER TABLE reporte DROP COLUMN tiempo_oracion;

-- 3. Agregar nuevos campos de información general
ALTER TABLE reporte
  ADD COLUMN diezmo       BIT(1)   NOT NULL DEFAULT 0 COMMENT '¿El líder diezmó esta semana?',
  ADD COLUMN lectura_biblia BIT(1) NOT NULL DEFAULT 0 COMMENT '¿El líder leyó la biblia esta semana?',
  ADD COLUMN visito        BIT(1)  NOT NULL DEFAULT 0 COMMENT '¿El líder visitó esta semana?',
  ADD COLUMN horas_oracion TINYINT NOT NULL DEFAULT 0 COMMENT 'Horas de oración del líder (0-23)',
  ADD COLUMN minutos_oracion TINYINT NOT NULL DEFAULT 0 COMMENT 'Minutos de oración del líder (0-59)';

-- =============================================================
-- VERIFICACIÓN: consulta para confirmar la estructura final
-- =============================================================
-- DESCRIBE reporte;
-- =============================================================
