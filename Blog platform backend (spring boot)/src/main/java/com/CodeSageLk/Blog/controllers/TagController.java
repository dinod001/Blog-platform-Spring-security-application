package com.CodeSageLk.Blog.controllers;

import com.CodeSageLk.Blog.domain.dtos.CreateTagsRequests;
import com.CodeSageLk.Blog.domain.dtos.TagResponseDto;
import com.CodeSageLk.Blog.domain.entities.Tag;
import com.CodeSageLk.Blog.mappers.TagMapper;
import com.CodeSageLk.Blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        List<Tag> allTags = tagService.getAllTags();
        List<TagResponseDto> tags = allTags.stream().map(tagMapper::toDto).toList();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<List<TagResponseDto>> createTags(@RequestBody CreateTagsRequests createTagsRequest) {
        List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
        List<TagResponseDto> createdTagRespons = savedTags.stream().map(tagMapper::toDto).toList();
        return new ResponseEntity<>(
                createdTagRespons,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path ="/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
