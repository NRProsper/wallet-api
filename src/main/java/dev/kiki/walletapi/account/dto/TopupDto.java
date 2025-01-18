package dev.kiki.walletapi.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TopupDto(
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "500.00", message = "Minimum top-up amount is 500 RWF")
        BigDecimal amount
) {}
