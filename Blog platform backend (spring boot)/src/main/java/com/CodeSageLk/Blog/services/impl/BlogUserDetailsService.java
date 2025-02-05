package com.CodeSageLk.Blog.services.impl;

import com.CodeSageLk.Blog.Security.BlogUserDetails;
import com.CodeSageLk.Blog.domain.entities.User;
import com.CodeSageLk.Blog.domain.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found this this " + email));
        return new BlogUserDetails(user);
    }
}
