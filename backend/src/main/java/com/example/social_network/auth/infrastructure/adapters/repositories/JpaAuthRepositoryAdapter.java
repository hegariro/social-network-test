package com.example.social_network.auth.infrastructure.adapters.repositories;

import java.util.Optional;
import java.util.UUID;

import com.example.social_network.api.v1.exception.BusinessException;
import com.example.social_network.auth.infrastructure.mappers.AuthMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.example.social_network.auth.domain.repositories.AuthRepository;
import com.example.social_network.auth.domain.models.User;
import com.example.social_network.auth.infrastructure.persistance.entities.UserEntity;

@Component
public class JpaAuthRepositoryAdapter implements AuthRepository {

    private final AuthMapper mapper;
    private final AuthJpaRepository jpaRepository;

    public JpaAuthRepositoryAdapter(AuthMapper mapper, AuthJpaRepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<User> validateCredentials(String nickname, String passwd) {
        Optional<UserEntity> response = jpaRepository.findByNicknameAndPassword(nickname, passwd);
        if (response.isEmpty()) {
            return Optional.empty();
        }
        return mapper.toDomain(response.get());
    }

    @Override
    public Optional<User> registry(String nickname, String name, String passwd) {
        Optional<UserEntity> searchNickname = jpaRepository.findByNickname(nickname);
        if (searchNickname.isPresent()) {
            throw new BusinessException("Nickname already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        String uuid = UUID.randomUUID().toString();
        UserEntity userEntity = jpaRepository.save(new UserEntity(uuid, nickname, name, passwd));
        return mapper.toDomain(userEntity);
    }
}
