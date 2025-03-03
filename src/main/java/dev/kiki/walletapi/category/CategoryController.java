package dev.kiki.walletapi.category;

import dev.kiki.walletapi.category.dto.CategoryDto;
import dev.kiki.walletapi.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "Endpoints for managing user categories")
@SecurityRequirement(name = "auth")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category for the authenticated user."
    )
    public ResponseEntity<Category> create(
            @Valid @RequestBody CategoryDto dto
            ) {
        var authenticatedUser = userService.getAuthenticatedUser();
        var createdCategory = categoryService.createCategory(authenticatedUser, dto);

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieves all categories for the authenticated user."
    )
    public ResponseEntity<List<Category>> getAll() {
        var authenticatedUser = userService.getAuthenticatedUser();
        var categories = categoryService.getAllCategories(authenticatedUser);

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/{category_id}")
    @Operation(
            summary = "Delete a category",
            description = "Deletes a category by its ID for the authenticated user."
    )
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable(name = "category_id") Integer categoryId
    ) {
        var authenticatedUser = userService.getAuthenticatedUser();
        categoryService.deleteCategory(categoryId, authenticatedUser);

        var response = Map.of(
                "message", "Category deleted"
        );

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{category_id}")
    @Operation(
            summary = "Update a category",
            description = "Updates a category by its ID for the authenticated user."
    )
    public ResponseEntity<Category> update(
            @PathVariable(name = "category_id") Integer categoryId,
            @RequestBody CategoryDto dto
    ) {
        var authenticatedUser = userService.getAuthenticatedUser();
        var updatedCategory = categoryService.updateCategory(categoryId, dto, authenticatedUser);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

    }

}
