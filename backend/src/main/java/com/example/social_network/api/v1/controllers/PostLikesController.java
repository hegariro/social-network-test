package com.example.social_network.api.v1.controllers;


import com.example.social_network.PostLikes.infrastructure.service.PostLikesCommand;
import com.example.social_network.shared.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/posts/")
@Tag(
        name = "Likes de publicaciones",
        description = "Endpoints para la gestión de likes asociados a una publicación."
)
public class PostLikesController {

    private final PostLikesCommand command;

    public PostLikesController(PostLikesCommand command) {
        this.command = command;
    }

    @GetMapping("/{idPost}")
    public ResponseEntity<?> getLikes(@PathVariable String idPost) {
        String idUser = getUserDetails().getId();
        command.setLikeToPost(idUser, idPost);
        return ResponseEntity.status(204).body("");
    }

    /**
     * Obtiene el CustomUserDetails de usuario autenticado a partir del token JWT.
     *
     * @return El CustomUserDetails del usuario autenticado.
     */
    private CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
