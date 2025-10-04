package com.example.social_network.auth.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.social_network.auth.application.ports.AuthCommand;
import com.example.social_network.auth.domain.repositories.AuthRepository;
import com.example.social_network.api.v1.dto.UserLoginResponse;

@Component
public class AuthUseCase implements AuthCommand {

    private final AuthRepository authRepository;

    public AuthUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    
    @Override
    public Optional<UserLoginResponse> login(String nickname, String password) {
        return authRepository.validateCredentials(nickname, password)
                .map(res -> new UserLoginResponse(res.token(), res.nickname()));
    }

    @Override
    public Optional<UserLoginResponse> registry(String nickname, String name, String password) {
        return authRepository.registry(nickname, name, password)
                .map(res -> new UserLoginResponse(res.token(), res.nickname()));
    }
}
