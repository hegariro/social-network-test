package com.example.social_network.api.v1.dto.userPosts;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserPostResponse(
    String id,
    String nickname,
    String title,
    String content,
    int likes,
    LocalDateTime createdAt
) { }
