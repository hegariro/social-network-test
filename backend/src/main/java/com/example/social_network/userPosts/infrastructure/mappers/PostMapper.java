package com.example.social_network.userPosts.infrastructure.mappers;

import com.example.social_network.userPosts.domain.models.Post;
import com.example.social_network.userPosts.infrastructure.persistance.entities.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostMapper {
    Optional<Post> toDomain(PostEntity entity);
    Optional<List<Post>> toDomain(List<PostEntity> entityList);
}
