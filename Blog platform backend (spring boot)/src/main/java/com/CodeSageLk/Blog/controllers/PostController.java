package com.CodeSageLk.Blog.controllers;

import com.CodeSageLk.Blog.domain.UpdatePostRequest;
import com.CodeSageLk.Blog.domain.dtos.CreatePostRequestDto;
import com.CodeSageLk.Blog.domain.dtos.PostDto;
import com.CodeSageLk.Blog.domain.dtos.UpdatePostRequestDto;
import com.CodeSageLk.Blog.domain.CreatePostRequest;
import com.CodeSageLk.Blog.domain.entities.Posts;
import com.CodeSageLk.Blog.domain.entities.User;
import com.CodeSageLk.Blog.mappers.PostMapper;
import com.CodeSageLk.Blog.services.PostService;
import com.CodeSageLk.Blog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId)
    {
        List<Posts> allPosts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> allPostsDto = allPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(allPostsDto);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostDto>> getDraftPosts(@RequestAttribute UUID id){
        User user = userService.getUserById(id);
        List<Posts> draftsPosts = postService.getDraftsPosts(user);
        return ResponseEntity.ok(draftsPosts.stream().map(postMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
           @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID id){
        User loggedUser = userService.getUserById(id);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Posts createdPost = postService.createPost(loggedUser, createPostRequest);
        PostDto CreatedPostdto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(CreatedPostdto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id,
                                  @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto){
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Posts UpdatedPost = postService.updatePost(id, updatePostRequest);
        PostDto UpdatedPostdto = postMapper.toDto(UpdatedPost);
        return new ResponseEntity<>(UpdatedPostdto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable UUID id
    ) {
        Posts post = postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
