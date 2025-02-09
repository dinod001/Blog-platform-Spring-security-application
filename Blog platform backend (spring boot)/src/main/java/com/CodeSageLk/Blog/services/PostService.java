package com.CodeSageLk.Blog.services;

import com.CodeSageLk.Blog.domain.CreatePostRequest;
import com.CodeSageLk.Blog.domain.UpdatePostRequest;
import com.CodeSageLk.Blog.domain.entities.Posts;
import com.CodeSageLk.Blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Posts> getAllPosts(UUID categoryId,UUID tagId);
    List<Posts> getDraftsPosts(User user);
    Posts createPost (User user, CreatePostRequest createPostRequest);
    Posts updatePost (UUID id, UpdatePostRequest updatePostRequest);
    Posts getPost(UUID id);
    void deletePost(UUID id);
}
