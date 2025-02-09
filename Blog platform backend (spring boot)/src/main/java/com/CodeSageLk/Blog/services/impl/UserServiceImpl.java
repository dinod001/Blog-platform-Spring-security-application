package com.CodeSageLk.Blog.services.impl;

import com.CodeSageLk.Blog.domain.entities.User;
import com.CodeSageLk.Blog.domain.repositories.UserRepo;
import com.CodeSageLk.Blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    @Override
    public User getUserById(UUID id) {
        return userRepo.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
    }
}
