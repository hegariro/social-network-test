package com.example.social_network.userPosts.infrastructure.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class PostEntity {

    @Id
    @Column(length = 36, updatable = false, nullable = false)
    private String id;
    private String title;
    private String content;
    private String nickname;
    @Column(nullable = false)
    private int likes = 0;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString(); // Genera el UUID
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now(); // Fecha de creación automática
        }
    }

    public PostEntity(String title, String content, String nickname) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }
}
