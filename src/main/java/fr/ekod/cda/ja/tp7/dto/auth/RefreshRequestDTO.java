package fr.ekod.cda.ja.tp7.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDTO(

        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
}
