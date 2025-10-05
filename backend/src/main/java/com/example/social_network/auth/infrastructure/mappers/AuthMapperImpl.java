package com.example.social_network.auth.infrastructure.mappers;

import com.example.social_network.auth.domain.models.User;
import com.example.social_network.auth.infrastructure.persistance.entities.UserEntity;
import com.example.social_network.shared.security.JwtUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthMapperImpl implements AuthMapper {

    private final JwtUtil jwtUtil;

    public AuthMapperImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<User> toDomain(UserEntity entity) {
        String token = jwtUtil.generateToken(entity.getId(), entity.getNickname());
        return Optional.of(new User(
            entity.getId(),
                entity.getNickname(),
                entity.getName(),
                token
        ));
    }
}
