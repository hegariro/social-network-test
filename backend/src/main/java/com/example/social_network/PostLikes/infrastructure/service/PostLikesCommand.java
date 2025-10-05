package com.example.social_network.PostLikes.infrastructure.service;

public interface PostLikesCommand {
    void setLikeToPost(String nickname, String idPost);
}
