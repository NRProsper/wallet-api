package dev.kiki.walletapi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetRequest(
    @Email(message = "Invalid email address")
    @NotBlank(message = "The Email address is required")
    String email

) {}
