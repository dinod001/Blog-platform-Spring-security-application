package com.CodeSageLk.Blog.domain.repositories;

import com.CodeSageLk.Blog.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {
    @Query("SELECT C FROM Category C LEFT JOIN FETCH C.posts")
    List<Category> findAllWithPostCounts();

    boolean existsByNameIgnoreCase(String name);
}
