package com.example.social_network.auth.domain.repositories;

import java.util.Optional;

public interface AuthService {
    Optional<String> getNicknameByUserId(String userId);
    Optional<String> getUserIdByNickname(String nickname);
}
