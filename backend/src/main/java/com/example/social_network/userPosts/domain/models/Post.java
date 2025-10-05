package com.example.social_network.userPosts.domain.models;

import java.time.LocalDateTime;

public record Post(
    String id, String title, String content, String nickname, int likes, LocalDateTime createdAt
) { }
