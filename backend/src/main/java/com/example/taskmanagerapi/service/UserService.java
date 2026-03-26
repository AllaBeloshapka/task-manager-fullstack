package com.example.taskmanagerapi.service;

import com.example.taskmanagerapi.dto.RegisterRequest;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.exception.UserAlreadyExistsException;
import com.example.taskmanagerapi.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service layer for user-related operations.
 *
 * Responsibilities:
 * - Handles user registration logic
 * - Validates business rules (e.g. unique username)
 * - Prepares entity before persistence
 */
@Service
@RequiredArgsConstructor
public class UserService {

    // Repository for user persistence operations
    private final UserRepository userRepository;

    /**
     * Registers a new user.
     *
     * Flow:
     * - Validate uniqueness of username
     * - Map request DTO to entity
     * - Apply default values (role, enabled, createdAt)
     * - Persist user
     *
     * Important:
     * - Password must be encrypted before saving (currently NOT implemented)
     *
     * @param request registration data
     * @return saved user entity
     */
    public User register(RegisterRequest request) {

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Create new user entity
        User user = new User();
        user.setUsername(request.getUsername());

        // TODO: Encrypt password using PasswordEncoder before saving
        user.setPassword(request.getPassword());

        // Set default values
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    /**
     * Initializes a default test user on application startup.
     *
     * Used for development and testing purposes.
     *
     * Note:
     * - Should be removed or replaced in production
     * - Password is stored in plain text (not secure)
     */
    @PostConstruct
    public void initUser() {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("test");

            // TODO: Encrypt password before saving
            user.setPassword("1234");

            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setCreatedAt(LocalDateTime.now());

            userRepository.save(user);
        }
    }
}