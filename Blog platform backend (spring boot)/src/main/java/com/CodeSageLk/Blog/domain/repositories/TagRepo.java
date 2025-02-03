package com.CodeSageLk.Blog.domain.repositories;

import com.CodeSageLk.Blog.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepo extends JpaRepository<Tag, UUID> {
}
