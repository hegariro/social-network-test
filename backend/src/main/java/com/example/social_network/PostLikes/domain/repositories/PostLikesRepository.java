package com.example.social_network.PostLikes.domain.repositories;

import com.example.social_network.PostLikes.domain.models.Like;

import java.util.Optional;

public interface PostLikesRepository {
    Optional<Like> persistLikeToPost(String nickname, String idPost);
}
