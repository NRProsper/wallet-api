package dev.kiki.walletapi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(

        @NotBlank(message = "Firstname required")
        @Size(min = 5, message = "Firstname must be at least 5 characters long")
        String firstName,

        @NotBlank(message = "Lastname required")
        @Size(min = 5, message = "Lastname must be at least 5 characters long")
        String lastName,

        @Email(message = "Invalid Email format")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password
) {}
