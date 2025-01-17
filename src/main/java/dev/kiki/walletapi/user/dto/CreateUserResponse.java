package dev.kiki.walletapi.user.dto;

import java.util.UUID;

public record CreateUserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
