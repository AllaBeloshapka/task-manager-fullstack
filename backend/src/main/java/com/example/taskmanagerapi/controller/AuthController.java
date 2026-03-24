package com.example.taskmanagerapi.controller;

import com.example.taskmanagerapi.dto.RegisterRequest;
import com.example.taskmanagerapi.dto.UserResponse;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller for authentication-related endpoints.
 * Handles user registration.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Registers a new user.
     */

    @GetMapping("/test")
    public String test() {
        return "Auth controller works";
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {

        User user = userService.register(request);

        // Convert entity to response DTO
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setEnabled(user.getEnabled());

        return response;
    }
}