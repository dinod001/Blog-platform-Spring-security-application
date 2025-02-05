package com.CodeSageLk.Blog.controllers;

import com.CodeSageLk.Blog.domain.dtos.AuthResponse;
import com.CodeSageLk.Blog.domain.dtos.LoginRequest;
import com.CodeSageLk.Blog.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth/login")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails user = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        AuthResponse response = AuthResponse.builder()
                .token(authenticationService.generateToken(user))
                .expiresIn(86400)
                .build();
        return ResponseEntity.ok(response);
    }

}
