package dev.kiki.walletapi.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ExpenseDto(
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "100.00", message = "Minimum expense amount is 100 RWF")
   BigDecimal amount,

   @NotNull(message = "category Id is required")
   @Positive(message = "Category id should not be negative")
   Integer categoryId
) {}
