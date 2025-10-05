-- =========================================
-- Script de inicialización de base de datos
-- Base de datos: periferia_db
-- =========================================

-- =========================================
-- Tabla: users
-- =========================================
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    passwd VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índice para mejorar las búsquedas por nickname
CREATE INDEX IF NOT EXISTS idx_users_nickname ON users(nickname);

-- =========================================
-- Datos de prueba (opcional)
-- =========================================
INSERT INTO users (id, nickname, name, passwd)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'admin', 'Administrador', 'M0d3T00R'),
    ('660e8400-e29b-41d4-a716-446655440001', 'testuser', 'Usuario de Prueba', 'T3stUs3r');

-- =========================================
-- Permisos (si es necesario)
-- =========================================
GRANT ALL PRIVILEGES ON TABLE users TO periferia_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO periferia_user;