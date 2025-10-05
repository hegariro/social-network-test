package com.example.social_network.userPosts.infrastructure.service;

import java.util.List;
import java.util.Optional;

import com.example.social_network.api.v1.dto.userPosts.UserPostResponse;

public interface UserPostsCommand {
    Optional<UserPostResponse> createNewPost(String nickname, String title, String content);
    Optional<List<UserPostResponse>> getAllPostByNickname(String nickname);
    Optional<UserPostResponse> getPostByIdAndNickname(String id, String nickname);
}
