package com.example.social_network.shared.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Sevicio Backend para la gestión de operaciones de la red social",
        version = "1.0",
        description = "Documentación de la API para la gestión de operaciones de la red social."
    ),
    security = @SecurityRequirement(name = "BearerAuth") 
)
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Primero realice login en el endpoint /v1/auth/login para obtener un token."
)
public class OpenApiConfig {
    // Esta clase no necesita más código. Las anotaciones hacen todo el trabajo.
}

