package com.CodeSageLk.Blog.controllers;

import com.CodeSageLk.Blog.domain.dtos.CategoryDto;
import com.CodeSageLk.Blog.domain.dtos.CreateCategoryRequests;
import com.CodeSageLk.Blog.domain.entities.Category;
import com.CodeSageLk.Blog.mappers.CategoryMapper;
import com.CodeSageLk.Blog.services.CategoryService;
import com.CodeSageLk.Blog.services.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
        List<Category> categories = categoryService.listCategories();
        return ResponseEntity.ok(categories.stream().
                map(categoryMapper::toDto).toList());

    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryRequests createCategoryRequests){
        Category category = categoryMapper.toEntity(createCategoryRequests);
        Category savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(
                categoryMapper.toDto(savedCategory),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable UUID id){
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
    }

}
