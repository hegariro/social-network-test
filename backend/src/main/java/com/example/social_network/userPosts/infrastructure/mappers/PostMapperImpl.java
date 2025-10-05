package com.example.social_network.userPosts.infrastructure.mappers;

import com.example.social_network.userPosts.domain.models.Post;
import com.example.social_network.userPosts.infrastructure.persistance.entities.PostEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostMapperImpl implements PostMapper {
    @Override
    public Optional<Post> toDomain(PostEntity entity) {
        return Optional.of(new Post(
            entity.getId(),
            entity.getTitle(),
            entity.getContent(),
            entity.getNickname(),
            entity.getLikes(),
            entity.getCreatedAt()
        ));
    }

    @Override
    public Optional<List<Post>> toDomain(List<PostEntity> entityList) {
        if(entityList.isEmpty()) {
            return Optional.empty();
        }

        List<Post> posts = new ArrayList<>();
        for(PostEntity entity :  entityList) {
            posts.add(new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getNickname(),
                entity.getLikes(),
                entity.getCreatedAt()
            ));
        }
        return Optional.of(posts);
    }
}
