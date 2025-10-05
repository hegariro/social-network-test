package com.example.social_network.auth.infrastructure.adapters.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.social_network.auth.infrastructure.persistance.entities.UserEntity;

@Repository
public interface AuthJpaRepository extends JpaRepository<UserEntity, String> {
    
    @Query("SELECT u FROM UserEntity u WHERE u.nickname = :nickname AND u.passwd = :passwd")
    Optional<UserEntity> findByNicknameAndPassword(
            @Param("nickname") String nickname,
            @Param("passwd") String passwd
    );

    @Query("SELECT u FROM UserEntity u WHERE u.nickname = :nickname")
    Optional<UserEntity> findByNickname(@Param("nickname") String nickname);
}
