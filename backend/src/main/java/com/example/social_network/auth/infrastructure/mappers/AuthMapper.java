package com.example.social_network.auth.infrastructure.mappers;

import com.example.social_network.auth.domain.models.User;
import com.example.social_network.auth.infrastructure.persistance.entities.UserEntity;

import java.util.Optional;

public interface AuthMapper {
    Optional<User> toDomain(UserEntity entity);
}
