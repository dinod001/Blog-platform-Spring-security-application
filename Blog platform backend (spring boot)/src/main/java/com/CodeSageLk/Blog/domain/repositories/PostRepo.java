package com.CodeSageLk.Blog.domain.repositories;

import com.CodeSageLk.Blog.domain.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Posts,UUID> {

}
