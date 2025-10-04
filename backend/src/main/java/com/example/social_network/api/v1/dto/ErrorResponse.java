package com.example.social_network.api.v1.dto;

import java.util.List;

public record ErrorResponse(
    List<ErrorResponseAttributes> errors
) {
    public record ErrorResponseAttributes(
        String status,
        String title,
        String detail
    ) { }
}
