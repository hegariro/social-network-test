package com.example.social_network.auth.domain.models;

public record User(
    String id, String nickname, String name, String token
) { }
