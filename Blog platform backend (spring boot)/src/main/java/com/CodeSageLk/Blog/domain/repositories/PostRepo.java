package com.CodeSageLk.Blog.domain.repositories;

import com.CodeSageLk.Blog.domain.PostStatus;
import com.CodeSageLk.Blog.domain.entities.Category;
import com.CodeSageLk.Blog.domain.entities.Posts;
import com.CodeSageLk.Blog.domain.entities.Tag;
import com.CodeSageLk.Blog.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Posts,UUID> {
    List<Posts> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Posts> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Posts> findAllByStatusAndTagsContaining(PostStatus status,  Tag tag);
    List<Posts> findAllByStatus(PostStatus status);
    List<Posts> findAllByAuthorAndStatus(User author, PostStatus status);

}
