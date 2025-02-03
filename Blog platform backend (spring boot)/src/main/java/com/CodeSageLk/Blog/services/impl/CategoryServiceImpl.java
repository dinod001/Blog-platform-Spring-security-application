package com.CodeSageLk.Blog.services.impl;

import com.CodeSageLk.Blog.domain.entities.Category;
import com.CodeSageLk.Blog.domain.repositories.CategoryRepo;
import com.CodeSageLk.Blog.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    public List<Category> listCategories(){
        return categoryRepo.findAllWithPostCounts();
    }

    @Override
    public Category createCategory(Category category) {
        if (categoryRepo.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        return categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            if (!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("There are no posts associated with this id");
            }
            categoryRepo.deleteById(id);
        }
    }

}
