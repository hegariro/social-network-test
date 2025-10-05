package com.example.social_network.auth.infrastructure.services;

import com.example.social_network.auth.domain.repositories.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthCommand authCommand;

    public AuthServiceImpl(AuthCommand authCommand) {
        this.authCommand = authCommand;
    }

    @Override
    public Optional<String> getNicknameByUserId(String userId) {
        return authCommand.getNicknameByUserId(userId);
    }

    @Override
    public Optional<String> getUserIdByNickname(String nickname) {
        return authCommand.getUserIdByNickname(nickname);
    }
}
