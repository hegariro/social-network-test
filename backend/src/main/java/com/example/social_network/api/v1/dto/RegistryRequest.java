package com.example.social_network.api.v1.dto;

public record RegistryRequest(
    String nickname, String name, String password
) { }
