package com.example.social_network.userPosts.domain.repositories;

import java.util.Optional;

public interface UserServicePort {
    Optional<String> getNicknameByUserId(String userId);
    Optional<String> getUserIdByNickname(String nickname);
}
