package dev.kiki.walletapi.user.dto;

import dev.kiki.walletapi.user.User;

import java.util.UUID;

public record LoginResponse(
        String message,
        String accessToken,
        Long expiresIn,
        SafeUser user
) {

    public record SafeUser(
            UUID id,
            String firstName,
            String lastName,
            String email
    ) {
        // Static method to convert User to SafeUser
        public static SafeUser fromUser(User user) {
            return new SafeUser(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail()
            );
        }
    }

}
