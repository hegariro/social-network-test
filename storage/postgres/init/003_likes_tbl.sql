CREATE TABLE IF NOT EXISTS post_likes (
    id BIGSERIAL PRIMARY KEY,
    idUser VARCHAR(36) NOT NULL,
    idPost VARCHAR(36) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_post_likes_user FOREIGN KEY (idUser) REFERENCES users(id),
    CONSTRAINT fk_post_likes_post FOREIGN KEY (idPost) REFERENCES posts(id)
);


-- =========================================
-- Permisos
-- =========================================

GRANT ALL PRIVILEGES ON TABLE post_likes TO periferia_user;
