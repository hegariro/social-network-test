-- =====================================================
-- Tabla: posts
-- Descripción: Almacena las publicaciones de los usuarios
-- =====================================================

CREATE TABLE posts (
   id VARCHAR(36) PRIMARY KEY,
   title VARCHAR(255),
   content TEXT,
   nickname VARCHAR(100),
   likes INT NOT NULL DEFAULT 0,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================================
-- Datos de prueba
-- =========================================

INSERT INTO posts (id, title, content, nickname, likes, created_at)
VALUES
    (
        '770e8400-e29b-41d4-a716-446655440010',
        'Bienvenida a la red social',
        '¡Hola a todos! Este es el primer post de nuestro administrador.',
        'admin',
        10,
        NOW()
    ),
    (
        '880e8400-e29b-41d4-a716-446655440011',
        'Primer post de prueba',
        'Probando la funcionalidad de publicación de usuarios en la red social.',
        'testuser',
        3,
        NOW()
    ),
    (
        '990e8400-e29b-41d4-a716-446655440012',
        'Segundo post de prueba',
        'Este es otro ejemplo de publicación generada por el usuario de prueba.',
        'testuser',
        5,
        NOW()
    );

-- =========================================
-- Permisos
-- =========================================

GRANT ALL PRIVILEGES ON TABLE posts TO periferia_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO periferia_user;