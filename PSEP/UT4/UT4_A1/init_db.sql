-- ============================================
-- init_db.sql
-- Crea BD + tabla + datos de ejemplo (5+)
-- MySQL / MariaDB
-- ============================================

DROP DATABASE IF EXISTS psep_ut4_api;
CREATE DATABASE psep_ut4_api
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE psep_ut4_api;

-- Tabla principal (ejemplo: productos)
DROP TABLE IF EXISTS productos;

CREATE TABLE productos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(120) NOT NULL,
  stock INT NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  fecha_alta DATE NOT NULL,
  PRIMARY KEY (id)
);

-- Datos de prueba (mínimo 5)
INSERT INTO productos (nombre, stock, precio, fecha_alta) VALUES
('Auriculares inalámbricos', 30, 39.99, '2025-01-10'),
('Teclado mecánico',         15, 79.50, '2025-01-12'),
('Ratón gaming',             50, 24.95, '2025-01-15'),
('Monitor 27 pulgadas',       8, 189.00,'2025-01-20'),
('Webcam HD',                22, 49.90, '2025-01-25');
