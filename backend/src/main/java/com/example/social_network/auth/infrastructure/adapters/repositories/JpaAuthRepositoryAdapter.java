package com.example.social_network.auth.infrastructure.adapters.repositories;

import java.util.Optional;
import java.util.UUID;

import com.example.social_network.api.v1.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.social_network.auth.domain.repositories.AuthRepository;
import com.example.social_network.auth.domain.models.User;
import com.example.social_network.shared.persistence.entities.UserEntity;
import com.example.social_network.shared.security.JwtUtil;

@Component
public class JpaAuthRepositoryAdapter implements AuthRepository {

    private final JwtUtil jwtUtil;
    private final AuthJpaRepository jpaRepository;

    public JpaAuthRepositoryAdapter(JwtUtil jwtUtil, AuthJpaRepository jpaRepository) {
        this.jwtUtil = jwtUtil;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<User> validateCredentials(String nickname, String passwd) {
        Optional<UserEntity> response = jpaRepository.findByNicknameAndPassword(nickname, passwd);
        if (response.isPresent()) {
            UserEntity user = response.get();
            String token = jwtUtil.generateToken(user.getId(), user.getNickname());
            return Optional.of(new User(
                user.getId(), user.getNickname(), user.getName(), token
            ));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> registry(String nickname, String name, String passwd) {
        Optional<UserEntity> searchNickname = jpaRepository.findByNickname(nickname);
        if (searchNickname.isPresent()) {
            throw new BusinessException("Nickname already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        String uuid = UUID.randomUUID().toString();
        UserEntity userEntity = jpaRepository.save(new UserEntity(uuid, nickname, name, passwd));
        String token = jwtUtil.generateToken(userEntity.getId(), userEntity.getNickname());
        return Optional.of(new User(
            userEntity.getId(), userEntity.getNickname(), userEntity.getNickname(), token
        ));
    }
}
