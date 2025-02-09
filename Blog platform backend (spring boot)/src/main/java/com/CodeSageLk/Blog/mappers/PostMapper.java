package com.CodeSageLk.Blog.mappers;

import com.CodeSageLk.Blog.domain.UpdatePostRequest;
import com.CodeSageLk.Blog.domain.dtos.CreatePostRequestDto;
import com.CodeSageLk.Blog.domain.dtos.PostDto;
import com.CodeSageLk.Blog.domain.CreatePostRequest;
import com.CodeSageLk.Blog.domain.dtos.UpdatePostRequestDto;
import com.CodeSageLk.Blog.domain.entities.Posts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toDto(Posts posts);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}
