package com.example.taskmanagerapi.service;

import com.example.taskmanagerapi.dto.RegisterRequest;
import com.example.taskmanagerapi.entity.EmailVerificationToken;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.exception.UserAlreadyExistsException;
import com.example.taskmanagerapi.repository.EmailVerificationTokenRepository;
import com.example.taskmanagerapi.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service layer for user-related operations.
 *
 * Responsibilities:
 * - Handles user registration logic
 * - Validates business rules (e.g. unique username)
 * - Prepares entity before persistence
 */
@Entity
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class UserService {

    // Repository for user persistence operations
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public User register(RegisterRequest request) {

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        // Create new user entity
        User user = new User();
        user.setUsername(request.getUsername());

        // TODO: Encrypt password using PasswordEncoder before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set default values
        user.setRole("ROLE_USER");
        user.setEnabled(false);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        //Сгенерировать токен
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

        EmailVerificationToken verificationToken = new EmailVerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiryDate(expiryDate);
        verificationToken.setUsed(false);

        emailVerificationTokenRepository.save(verificationToken);

        return savedUser;
    }

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
    //Сгенерировать токен
    String token = UUID.randomUUID().toString();
    LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
}