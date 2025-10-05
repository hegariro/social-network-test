package com.example.social_network.userPosts.infrastructure.mappers;

import com.example.social_network.userPosts.domain.models.Post;
import com.example.social_network.userPosts.infrastructure.persistance.entities.PostEntity;

import java.util.Optional;

public interface PostMapper {
    Optional<Post> toDomain(PostEntity entity);
}
