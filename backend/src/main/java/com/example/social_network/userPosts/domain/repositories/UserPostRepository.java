package com.example.social_network.userPosts.domain.repositories;

import com.example.social_network.api.v1.dto.userPosts.UserPostResponse;
import com.example.social_network.userPosts.domain.models.Post;

import java.util.List;
import java.util.Optional;

public interface UserPostRepository {
    Optional<Post> newPost(String nickname, String title, String content);
    Optional<List<Post>> getAllPosts();
    Optional<List<Post>> getPosts(String nickname);
    Optional<Post> getPosts(String nickname, String id);
}
