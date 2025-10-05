package com.example.social_network.PostLikes.infrastructure.adapters.repositories;

import com.example.social_network.PostLikes.domain.models.Like;
import com.example.social_network.PostLikes.domain.repositories.PostLikesRepository;
import com.example.social_network.PostLikes.infrastructure.mappers.PostLikesMapper;
import com.example.social_network.PostLikes.infrastructure.persistance.entities.LikeEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaPostLikesRepositoryAdapter implements PostLikesRepository {

    private final PostLikesMapper mapper;
    private final PostLikesJpaRepository repository;

    public JpaPostLikesRepositoryAdapter(PostLikesMapper mapper, PostLikesJpaRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Optional<Like> persistLikeToPost(String nickname, String idPost) {
        LikeEntity entity = repository.save(mapper.toEntity(nickname, idPost));
        return mapper.toDomain(entity);
    }
}
