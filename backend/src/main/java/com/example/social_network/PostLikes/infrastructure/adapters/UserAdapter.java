package com.example.social_network.PostLikes.infrastructure.adapters;

import com.example.social_network.PostLikes.domain.repositories.UserServicePort;
import com.example.social_network.auth.domain.repositories.AuthService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
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
