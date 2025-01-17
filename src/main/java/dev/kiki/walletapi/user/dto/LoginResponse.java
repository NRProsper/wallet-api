package dev.kiki.walletapi.user.dto;

public record LoginResponse(
        String message,
        String accessToken,
        Long expiresIn
) {
}
