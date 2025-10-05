package com.example.social_network.auth.domain.repositories;

import java.util.Optional;
import com.example.social_network.auth.domain.models.User;

public interface AuthRepository {
    Optional<User> validateCredentials(String nickname, String passwd);
    Optional<User> registry(String nickname, String name, String passwd);
    Optional<String> findNicknameByUserId(String userId);
    Optional<String> findUserIdByNickname(String nickname);
}
