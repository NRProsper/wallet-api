package dev.kiki.walletapi.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryDto(

        @NotBlank(message = "Category name is required")
        @Size(min = 3, message = "Category name should be at least 3 characters")
        String name,

        @Pattern(
                regexp = "[\uD83C-\uDBFF\uDC00-\uDFFF]+",
                message = "Invalid emoji. Please provide a valid emoji character."
        )
        String emoji
) {
}
