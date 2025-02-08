package com.CodeSageLk.Blog.controllers;

import com.CodeSageLk.Blog.domain.dtos.AuthResponse;
import com.CodeSageLk.Blog.domain.dtos.LoginRequest;
import com.CodeSageLk.Blog.services.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userdetails = authenticationService.autheticate
                (loginRequest.getEmail(), loginRequest.getPassword());
        AuthResponse authResponse = AuthResponse.builder()
                .token(authenticationService.generateToken(userdetails))
                .expiresIn(86400)
                .build();

        return ResponseEntity.ok(authResponse);
    }

}
