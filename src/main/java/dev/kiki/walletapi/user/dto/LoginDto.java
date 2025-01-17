package dev.kiki.walletapi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @Email(message = "Invalid email address")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {}
