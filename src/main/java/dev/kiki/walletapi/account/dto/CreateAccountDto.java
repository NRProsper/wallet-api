package dev.kiki.walletapi.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateAccountDto(
    @NotBlank(message = "Account type is required")
    @Size(min = 5, message = "Account type name should be at least 5 characters long")
    String type,

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance can only be zero or Positive number")
    BigDecimal balance

) {}
