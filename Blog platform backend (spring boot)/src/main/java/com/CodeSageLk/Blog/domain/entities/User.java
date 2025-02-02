package com.CodeSageLk.Blog.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posts> posts=new ArrayList<>();

    // We need to implement equals and hashCode explicitly because
    // Lombok's implementation can cause issues with JPA entities


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, createdAt);
    }


    //this method only call when this entity is firstly call, that mena when the tables are generated in the database
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
