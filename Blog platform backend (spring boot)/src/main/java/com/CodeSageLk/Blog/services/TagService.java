package com.CodeSageLk.Blog.services;

import com.CodeSageLk.Blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<Tag> getAllTags();
    List<Tag> createTags(Set<String> tags);
    void deleteTag(UUID tagId);
    Tag getTagById(UUID tagId);
    List<Tag> getTagByIds(Set<UUID> tagIds);
}
