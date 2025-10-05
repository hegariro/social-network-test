package com.example.social_network.PostLikes.application.usecases;

import com.example.social_network.PostLikes.domain.repositories.PostLikesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostLikesUseCase {

    private final PostLikesRepository repository;

    public PostLikesUseCase(PostLikesRepository repository) {
        this.repository = repository;
    }

    public void setLikeToPost(String nickname, String idPost) {
        if ((repository.persistLikeToPost(nickname, idPost)).isEmpty()) {
            log.error("No se ha podido persistir el like de {} a la publicación {}", nickname, idPost);
        }
        // emitir evento para la cola de mensajes
    }
}
