package com.example.social_network.PostLikes.infrastructure.mappers;

import com.example.social_network.PostLikes.domain.models.Like;
import com.example.social_network.PostLikes.infrastructure.persistance.entities.LikeEntity;

import java.util.List;
import java.util.Optional;

public interface PostLikesMapper {
    Optional<Like> toDomain(LikeEntity entity);
    LikeEntity toEntity(String nickname, String idPost);
}
