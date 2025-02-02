package com.CodeSageLk.Blog.domain.entities;

import com.CodeSageLk.Blog.domain.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)//this will store our enum as string instead on int in database
    private PostStatus status;
    @Column(nullable = false)
    private Integer readingTime;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Posts posts = (Posts) o;
        return Objects.equals(id, posts.id) && Objects.equals(title, posts.title) && Objects.equals(content, posts.content) && status == posts.status && Objects.equals(readingTime, posts.readingTime) && Objects.equals(createdAt, posts.createdAt) && Objects.equals(updatedAt, posts.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, status, readingTime, createdAt, updatedAt);
    }


    // this will only work when the entity is created and that mean when the database table generated
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //this will work when the entity is updated, that mean datbase table updated
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
