package com.example.social_network.userPosts.infrastructure.adapters.repositories;

import com.example.social_network.api.v1.dto.userPosts.UserPostResponse;
import com.example.social_network.userPosts.domain.models.Post;
import com.example.social_network.userPosts.domain.repositories.UserPostRepository;
import com.example.social_network.userPosts.infrastructure.mappers.PostMapper;
import com.example.social_network.userPosts.infrastructure.persistance.entities.PostEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class JpaUserPostRepositoryAdapter implements UserPostRepository {

    private final PostMapper mapper;
    private final UserPostJpaRepository jpaRepository;

    public JpaUserPostRepositoryAdapter(PostMapper mapper, UserPostJpaRepository jpaRepository) {
        this.mapper = mapper;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Post> newPost(String nickname, String title, String content) {
        PostEntity response = jpaRepository.save(new PostEntity(title, content, nickname));
        return mapper.toDomain(response);
    }
}
