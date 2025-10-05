package com.example.social_network.userPosts.infrastructure.adapters.repositories;

import com.example.social_network.userPosts.infrastructure.persistance.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPostJpaRepository extends JpaRepository<PostEntity, String>
{
    @Query("SELECT p FROM PostEntity p WHERE p.nickname = :nickname")
    List<PostEntity> getPostEntitiesByNickname(@Param("nickname") String nickname);

    @Query("SELECT p FROM PostEntity p WHERE p.nickname = :nickname AND p.id = :id")
    PostEntity getPostEntitiesByIdAndNickname(@Param("nickname") String nickname, @Param("id") String id);
}
