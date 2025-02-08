package com.CodeSageLk.Blog.services;

import org.springframework.security.core.userdetails.UserDetails;
public interface AuthenticationService {
    UserDetails autheticate(String email, String password);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);
}
