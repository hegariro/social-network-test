package com.example.social_network.PostLikes.infrastructure.persistance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_likes")
@Getter
@Setter
@NoArgsConstructor
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String idUser;
    private String idPost;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public LikeEntity(String idUser, String idPost) {
        this.idUser = idUser;
        this.idPost = idPost;
    }
}
