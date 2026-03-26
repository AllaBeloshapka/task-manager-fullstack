package com.example.taskmanagerapi.controller;

import com.example.taskmanagerapi.dto.RegisterRequest;
import com.example.taskmanagerapi.dto.UserResponse;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller responsible for authentication-related operations.
 * Currently supports user registration and a simple health-check endpoint.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    // Service layer dependency for user-related business logic
    private final UserService userService;

    /**
     * Simple test endpoint to verify that the controller is reachable.
     * Useful for quick checks during development or debugging.
     *
     * @return plain text confirmation message
     */
    @GetMapping("/test")
    public String test() {
        return "Auth controller works";
    }

    /**
     * Handles user registration.
     *
     * Flow:
     * 1. Validates incoming request payload
     * 2. Delegates user creation to the service layer
     * 3. Maps the created entity to a response DTO
     *
     * Note:
     * - Password hashing and validation should be handled in the service layer
     * - This controller should remain thin and not contain business logic
     *
     * @param request validated registration request DTO
     * @return UserResponse DTO with basic user information (no sensitive data)
     */
    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {

        // Delegate user creation to service layer
        User user = userService.register(request);

        // Map entity to response DTO (manual mapping for now)
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setEnabled(user.getEnabled());

        return response;
    }
}