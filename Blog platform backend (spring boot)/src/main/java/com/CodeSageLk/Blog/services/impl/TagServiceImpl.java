package com.CodeSageLk.Blog.services.impl;

import com.CodeSageLk.Blog.domain.entities.Tag;
import com.CodeSageLk.Blog.domain.repositories.TagRepo;
import com.CodeSageLk.Blog.services.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;
    @Override
    public List<Tag> getAllTags() {
        return tagRepo.findAllWithPostCount();
    }

    @Override
    public List<Tag> createTags(Set<String> tagsNames) {
        Set<Tag> existingTags = tagRepo.findByNameInIgnoreCase(tagsNames);
        Set<String> existingTagNames = existingTags.stream().
                map(Tag::getName).collect(Collectors.toSet());

        List<Tag> newTags = tagsNames.stream().filter(tagName -> !existingTagNames.contains(tagName))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()) {
            savedTags=tagRepo.saveAll(newTags);
        }
        savedTags.addAll(existingTags);
        return savedTags;
    }

    @Override
    public void deleteTag(UUID tagId) {
        tagRepo.findById(tagId).ifPresent(tag -> {
            if (tag.getPosts().isEmpty()) {
                tagRepo.deleteById(tagId);
            }else{
                throw new IllegalStateException("Cannot delete tag with posts");
            }
        });
    }

}
