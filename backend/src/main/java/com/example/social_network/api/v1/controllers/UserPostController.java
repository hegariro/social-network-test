package com.example.social_network.api.v1.controllers;

import com.example.social_network.api.v1.dto.userPosts.UserPostRequest;
import com.example.social_network.api.v1.dto.userPosts.UserPostResponse;
import com.example.social_network.api.v1.exception.BusinessException;
import com.example.social_network.userPosts.application.ports.UserPostsCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user/posts")
@Tag(
    name = "Publicaciones de usuario",
    description = "Endpoints para la gestión de publicaciones creadas por los usuarios autenticados."
)
public class UserPostController {
    private final UserPostsCommand userPostsCommand;

    public UserPostController(UserPostsCommand userPostsCommand) {
        this.userPostsCommand = userPostsCommand;
    }

    /**
     * Obtiene el nickname de usuario autenticado a partir del token JWT.
     *
     * @return El nickname del usuario autenticado.
     * @throws BusinessException si no hay un usuario autenticado.
     */
    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException("Usuario no autenticado", HttpStatus.FORBIDDEN);
        }
        return authentication.getName();
    }

    /**
     * Crea una nueva publicación asociada al usuario autenticado.
     *
     * @param request Objeto con los datos de la publicación (título y contenido).
     * @return La publicación creada.
     */
    @Operation(
            summary = "Crear nueva publicación",
            description = "Permite a un usuario autenticado crear una nueva publicación con título y contenido."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Publicación creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserPostResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuario no autenticado o token inválido",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Los datos de la publicación no pudieron ser procesados",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public ResponseEntity<?> newUserPost(@RequestBody UserPostRequest request) {
        String nickname = getAuthenticatedUsername();
        Optional<UserPostResponse> userPostResponse = userPostsCommand.createNewPost(
            nickname, request.title(), request.content()
        );

        if (userPostResponse.isEmpty()) {
            throw new BusinessException(
                    "Los datos de la publicación no pudieron ser procesados",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        return ResponseEntity.status(201).body(userPostResponse.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllPost() {
        String nickname = getAuthenticatedUsername();
        Optional<List<UserPostResponse>> response = userPostsCommand.getAllPostByNickname(nickname);
        if (response.isEmpty()) {
            throw new BusinessException(
                "Los datos de usuario son incorrectos o no tiene publicaciones asociadas",
                HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        return ResponseEntity.status(200).body(response.get());
    }

    @GetMapping("/{idPost}")
    public ResponseEntity<?> getPostById(@PathVariable String idPost) {
        String nickname = getAuthenticatedUsername();
        Optional<UserPostResponse> response = userPostsCommand.getPostByIdAndNickname(idPost, nickname);
        if (response.isEmpty()) {
            throw new BusinessException(
                    "El id del post es incorrecto o no existe la publicación asociada",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }

        return ResponseEntity.status(200).body(response.get());
    }
}
