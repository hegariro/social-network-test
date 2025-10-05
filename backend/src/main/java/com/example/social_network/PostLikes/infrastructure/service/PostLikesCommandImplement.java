package com.example.social_network.PostLikes.infrastructure.service;

import com.example.social_network.PostLikes.application.usecases.PostLikesUseCase;
import org.springframework.stereotype.Component;

@Component
public class PostLikesCommandImplement implements PostLikesCommand {

    private final PostLikesUseCase useCase;

    public PostLikesCommandImplement(PostLikesUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void setLikeToPost(String nickname, String idPost) {
        useCase.setLikeToPost(nickname, idPost);
    }
}
