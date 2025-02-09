package com.CodeSageLk.Blog.services.impl;

import com.CodeSageLk.Blog.domain.CreatePostRequest;
import com.CodeSageLk.Blog.domain.PostStatus;
import com.CodeSageLk.Blog.domain.UpdatePostRequest;
import com.CodeSageLk.Blog.domain.entities.*;
import com.CodeSageLk.Blog.domain.repositories.PostRepo;
import com.CodeSageLk.Blog.services.CategoryService;
import com.CodeSageLk.Blog.services.PostService;
import com.CodeSageLk.Blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final CategoryService categoryService;
    private final TagService tagService;
    private static final int WORDS_PER_MINUTE=200;

    @Override
    public List<Posts> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId!=null && tagId!=null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);

            return postRepo.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (tagId!=null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepo.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        if (categoryId!=null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepo.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        return postRepo.findAllByStatus(PostStatus.PUBLISHED);
    }

    public List<Posts> getDraftsPosts(User user){
        return postRepo.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    public Posts createPost(User user, CreatePostRequest createPostRequest) {
        Posts newPost = new Posts();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setAuthor(user);
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setReadingTime(calCulateTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);
        List<Tag> allTags = tagService.getTagByIds(createPostRequest.getTagIds());
        newPost.setTags(new HashSet<>(allTags));

        return postRepo.save(newPost);
    }

    @Override
    public Posts updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Posts existingPost = postRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("posts not found with this ID"));
        existingPost.setTitle(updatePostRequest.getTitle());
        existingPost.setContent(updatePostRequest.getContent());
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calCulateTime(updatePostRequest.getContent()));
        UUID UpdatePostRequestcategoryId = updatePostRequest.getCategoryId();

        if (!existingPost.getCategory().getId().equals(UpdatePostRequestcategoryId)) {
            updatePostRequest.setCategoryId(categoryService.getCategoryById(UpdatePostRequestcategoryId).getId());
        }

        Set<UUID> existingTags = existingPost.getTags().stream().
                map(Tag::getId).collect(Collectors.toSet());

        Set<UUID> upadativetags = updatePostRequest.getTagIds();

        if (!existingTags.equals(upadativetags)) {
            List<Tag> newTags = tagService.getTagByIds(upadativetags);
            existingPost.setTags(new HashSet<>(newTags));
        }

        return postRepo.save(existingPost);
    }

    @Override
    public Posts getPost(UUID id) {
        return postRepo.findById(id).orElseThrow(()->
                new EntityNotFoundException("posts not found with this ID"));
    }

    @Override
    public void deletePost(UUID id) {
        Posts post = getPost(id);
        postRepo.delete(post);
    }

    private Integer calCulateTime(String content) {
        if (content==null || content.isEmpty()){
            return 0;
        }
        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double)wordCount/WORDS_PER_MINUTE);
    }
}
