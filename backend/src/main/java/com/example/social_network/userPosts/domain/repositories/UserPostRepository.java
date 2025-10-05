package com.example.social_network.userPosts.domain.repositories;

import com.example.social_network.userPosts.domain.models.Post;

import java.util.Optional;

public interface UserPostRepository {
    Optional<Post> newPost(String nickname, String title, String content);
}
