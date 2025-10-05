package com.example.social_network.api.v1.controllers;

// imports
import java.util.List;
import java.util.Optional;

import com.example.social_network.api.v1.dto.auth.CredentialsRequest;
import com.example.social_network.api.v1.dto.ErrorResponse;
import com.example.social_network.api.v1.dto.auth.RegistryRequest;
import com.example.social_network.api.v1.dto.auth.UserLoginResponse;
import com.example.social_network.api.v1.exception.BusinessException;
import com.example.social_network.auth.infrastructure.services.AuthCommand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Autenticación", description = "Operaciones de autenticación para la API")
public class AuthController {
    
    private final AuthCommand authCommand;

    public AuthController(AuthCommand authCommand) {
        this.authCommand = authCommand;
    }


    // OpenAPI Docs
    @Operation(
        summary = "Autentica a un usuario y genera un token JWT",
        description = "Valida las credenciales del usuario y, si son válidas, devuelve un token JWT para acceder a los recursos protegidos."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Credenciales del usuario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CredentialsRequest.class)
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "Autenticación exitosa",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UserLoginResponse.class)
        )
    )
    @ApiResponse(
        responseCode = "401",
        description = "Credenciales inválidas",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
        )
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CredentialsRequest credentials) {
        Optional<UserLoginResponse> authResponse = authCommand
            .login(credentials.nickname(), credentials.passwd());
        if (authResponse.isPresent()) {
            UserLoginResponse response = authResponse.get();
            return ResponseEntity.ok().body(response);
        }

        ErrorResponse response = new ErrorResponse(List.of(
            new ErrorResponse.ErrorResponseAttributes(
                HttpStatusCode.valueOf(401).toString(),
                "Invalid credentials",
                "Invalid credentials"
            )
        ));
        return ResponseEntity.status(401).body(response);
    }

    // OpenAPI Docs
    @Operation(
            summary = "Registra a un usuario y genera un token JWT",
            description = "Valida que el nickname no se encuentre registrado, si no está registrado, devuelve un token JWT para acceder a los recursos protegidos."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de usuario",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RegistryRequest.class)
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "Autenticación exitosa",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserLoginResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "422",
            description = "La entidad no es procesable",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
            )
    )
    @PostMapping("/registry")
    public ResponseEntity<?> registry(@RequestBody RegistryRequest payload) {
        Optional<UserLoginResponse> authResponse = authCommand
            .registry(payload.nickname(), payload.name(), payload.password());
        if (authResponse.isEmpty()) {
            throw new BusinessException("Datos incorrectos", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.status(201).body(authResponse.get());
    }

    final Optional<String> getNicknameByUserId(String userId) {
        return authCommand.getNicknameByUserId(userId);
    }

    final Optional<String> getUserIdByNickname(String nickname) {
        return authCommand.getUserIdByNickname(nickname);
    }
}
