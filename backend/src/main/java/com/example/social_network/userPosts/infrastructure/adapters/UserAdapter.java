package com.example.social_network.userPosts.infrastructure.adapters;

import com.example.social_network.auth.domain.repositories.AuthService;
import com.example.social_network.userPosts.domain.repositories.UserServicePort;

import java.util.Optional;

public class UserAdapter implements UserServicePort {

    private final AuthService authService;

    public UserAdapter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Optional<String> getNicknameByUserId(String userId) {
        return authService.getNicknameByUserId(userId);
    }

    @Override
    public Optional<String> getUserIdByNickname(String nickname) {
        return authService.getUserIdByNickname(nickname);
    }
}
