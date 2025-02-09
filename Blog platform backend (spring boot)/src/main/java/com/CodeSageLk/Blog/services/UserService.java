package com.CodeSageLk.Blog.services;

import com.CodeSageLk.Blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
