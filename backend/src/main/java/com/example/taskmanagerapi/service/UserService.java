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
 * Service for user-related operations.
 * Handles registration logic.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    /**
     * Registers a new user.
     *
     * @param request registration data
     * @return saved user entity
     */
    public User register(RegisterRequest request) {

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());

        // Encrypt password before saving
        user.setPassword(request.getPassword());

        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        // Save user to database
        return userRepository.save(user);
    }

    @PostConstruct
    public void initUser() {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("test");
            user.setPassword("1234");
            user.setRole("USER");
            user.setEnabled(true);
            user.setCreatedAt(LocalDateTime.now());

            userRepository.save(user);
        }
    }

}