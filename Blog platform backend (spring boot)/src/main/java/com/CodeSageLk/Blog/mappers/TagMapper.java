package com.CodeSageLk.Blog.mappers;

import com.CodeSageLk.Blog.domain.PostStatus;
import com.CodeSageLk.Blog.domain.dtos.TagResponseDto;
import com.CodeSageLk.Blog.domain.entities.Posts;
import com.CodeSageLk.Blog.domain.entities.Tag;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagResponseDto toDto(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Posts> posts) {
        if (posts == null || posts.isEmpty()) {
            return 0;
        }
        return (int) posts.stream().
                filter(Post->Post.getStatus().equals(PostStatus.PUBLISHED)).count();

    }
}
