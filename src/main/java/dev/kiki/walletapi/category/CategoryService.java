package dev.kiki.walletapi.category;

import dev.kiki.walletapi.category.dto.CategoryDto;
import dev.kiki.walletapi.exception.ResourceNotFoundException;
import dev.kiki.walletapi.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(User authenticatedUser, CategoryDto dto) {
        var category = new Category();
        category.setName(dto.name());
        category.setUser(authenticatedUser);

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(User authenticatedUser) {
        return categoryRepository.findAllByUser(authenticatedUser);
    }

    public Category updateCategory(Integer categoryId, CategoryDto dto, User authenticatedUser) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!category.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Category does not belong to the authenticated user");
        }

        category.setName(dto.name());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer categoryId, User authenticatedUser) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Category does not belong to the authenticated user");
        }


        categoryRepository.delete(category);
    }


}
