package com.CodeSageLk.Blog.mappers;

import com.CodeSageLk.Blog.domain.PostStatus;
import com.CodeSageLk.Blog.domain.dtos.CategoryDto;
import com.CodeSageLk.Blog.domain.dtos.CreateCategoryRequests;
import com.CodeSageLk.Blog.domain.entities.Category;
import com.CodeSageLk.Blog.domain.entities.Posts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount",source = "posts",qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Posts> posts) {
        if (posts == null || posts.isEmpty()) {
            return 0;
        }
        return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }

    Category toEntity(CreateCategoryRequests createCategoryRequests);

    /**
     * This mapper converts a Category entity into a CategoryDto.
     *
     * - Uses MapStruct to automatically map fields.
     * - `postCount` in CategoryDto is calculated based on the number of PUBLISHED posts.
     * - `source = "posts"` means the `posts` list from Category is passed to `calculatePostCount`.
     * - `calculatePostCount` filters and counts only PUBLISHED posts.
     * - `@Mapper(componentModel = "spring")` makes it a Spring-managed bean for dependency injection.
     * - `unmappedTargetPolicy = ReportingPolicy.IGNORE` ensures no errors for unmapped fields.
     *
     * 📌 **Example Scenario:**
     *
     * Given a Category with posts:
     *
     * Category:
     *   - name: "Tech"
     *   - posts: [ "Java" (PUBLISHED), "Spring" (DRAFT), "Hibernate" (PUBLISHED) ]
     *
     * When `toDto(category)` is called:
     *   - `name` is mapped directly.
     *   - `posts` is passed as `source` to `calculatePostCount`.
     *   - `calculatePostCount` counts only PUBLISHED posts (Java, Hibernate → count = 2).
     *   - Result: `CategoryDto(name="Tech", postCount=2)`.
     */

}
