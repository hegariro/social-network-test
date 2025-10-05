package com.example.social_network.PostLikes.domain.models;

import java.time.LocalDateTime;

public record Like(long id, String nickname, String idPost, LocalDateTime createdAt) {
}
