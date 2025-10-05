package com.example.social_network.PostLikes.infrastructure.mappers;

import com.example.social_network.PostLikes.domain.models.Like;
import com.example.social_network.PostLikes.infrastructure.adapters.UserAdapter;
import com.example.social_network.PostLikes.infrastructure.persistance.entities.LikeEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostLikesImpl implements PostLikesMapper {

    private final UserAdapter userService;

    public PostLikesImpl(UserAdapter userService) {
        this.userService = userService;
    }

    @Override
    public Optional<Like> toDomain(LikeEntity entity) {
        Optional<String> res = getNicknameByIdUser(entity.getIdUser());
        String nickname= "Anonymous";
        if (res.isPresent()) {
            nickname = res.get();
        }
        return Optional.of(new Like(
            entity.getId(),
            nickname,
            entity.getIdPost(),
            entity.getCreatedAt()
        ));
    }

    @Override
    public LikeEntity toEntity(String nickname, String idPost) {
        String userId = (userService.getUserIdByNickname(nickname)).get();
        return new LikeEntity(userId, idPost);
    }

    private Optional<String> getNicknameByIdUser(String idUser) {
        return userService.getNicknameByUserId(idUser);
    }
}
