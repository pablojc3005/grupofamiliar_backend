-- ================================================================
-- Script de Migración: Eliminar columna id_periodo de la tabla reporte
-- Ejecutar manualmente en MariaDB/MySQL ANTES de reiniciar la aplicación.
-- ================================================================

USE grupofamiliar;

-- 1. Eliminar la foreign key constraint de id_periodo (si existe)
SET @fk_name = (
    SELECT CONSTRAINT_NAME
    FROM information_schema.KEY_COLUMN_USAGE
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'reporte'
      AND COLUMN_NAME = 'id_periodo'
      AND REFERENCED_TABLE_NAME IS NOT NULL
    LIMIT 1
);

SET @drop_fk_sql = IF(@fk_name IS NOT NULL,
    CONCAT('ALTER TABLE reporte DROP FOREIGN KEY ', @fk_name),
    'SELECT 1'
);
PREPARE stmt FROM @drop_fk_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2. Eliminar la columna id_periodo (solo si existe)
SET @col_exists = (
    SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'reporte'
      AND COLUMN_NAME = 'id_periodo'
);

SET @drop_col_sql = IF(@col_exists > 0,
    'ALTER TABLE reporte DROP COLUMN id_periodo',
    'SELECT 1'
);
PREPARE stmt2 FROM @drop_col_sql;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;

-- 3. Eliminar columnas de movimiento_financiero (id_periodo) si existe
SET @fk2 = (
    SELECT CONSTRAINT_NAME
    FROM information_schema.KEY_COLUMN_USAGE
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'movimiento_financiero'
      AND COLUMN_NAME = 'id_periodo'
      AND REFERENCED_TABLE_NAME IS NOT NULL
    LIMIT 1
);

SET @drop_fk2 = IF(@fk2 IS NOT NULL,
    CONCAT('ALTER TABLE movimiento_financiero DROP FOREIGN KEY ', @fk2),
    'SELECT 1'
);
PREPARE stmt3 FROM @drop_fk2;
EXECUTE stmt3;
DEALLOCATE PREPARE stmt3;

SET @col2_exists = (
    SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'movimiento_financiero'
      AND COLUMN_NAME = 'id_periodo'
);

SET @drop_col2 = IF(@col2_exists > 0,
    'ALTER TABLE movimiento_financiero DROP COLUMN id_periodo',
    'SELECT 1'
);
PREPARE stmt4 FROM @drop_col2;
EXECUTE stmt4;
DEALLOCATE PREPARE stmt4;

-- 4. Limpiar usuarios duplicados (mantener solo el de ID más bajo por email)
DELETE u1 FROM usuario u1
INNER JOIN usuario u2
  ON u1.email = u2.email AND u1.id > u2.id;

-- Verificar resultado
SELECT 'Migración completada exitosamente' AS resultado;
SELECT id, email, nombres FROM usuario ORDER BY email, id;
