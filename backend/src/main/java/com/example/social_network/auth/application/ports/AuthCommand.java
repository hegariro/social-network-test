package com.example.social_network.auth.application.ports;

import com.example.social_network.api.v1.dto.auth.UserLoginResponse;
import java.util.Optional;

public interface AuthCommand {
    Optional<UserLoginResponse> login(String nickname, String password);
    Optional<UserLoginResponse> registry(String nickname, String name, String password);
}
