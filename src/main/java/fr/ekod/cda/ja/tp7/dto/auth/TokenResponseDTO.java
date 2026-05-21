package fr.ekod.cda.ja.tp7.dto.auth;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn
) {
}
