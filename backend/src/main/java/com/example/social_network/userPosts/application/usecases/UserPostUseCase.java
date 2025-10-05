package com.example.social_network.userPosts.application.usecases;

import com.example.social_network.api.v1.dto.userPosts.UserPostResponse;
import com.example.social_network.userPosts.application.ports.UserPostsCommand;
import com.example.social_network.userPosts.domain.repositories.UserPostRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPostUseCase implements UserPostsCommand {

    private final UserPostRepository userPostRepository;

    public UserPostUseCase(UserPostRepository userPostRepository) {
        this.userPostRepository = userPostRepository;
    }

    @Override
    public Optional<UserPostResponse> createNewPost(String nickname, String title, String content) {
        return userPostRepository.newPost(nickname, title, content)
            .map(res -> new UserPostResponse(
                res.id(), res.nickname(), res.title(), res.content(), res.likes(), res.createdAt()
            ));
    }
}
