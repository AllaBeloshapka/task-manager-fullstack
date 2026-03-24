package com.example.taskmanagerapi.repository;

import com.example.taskmanagerapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by username
     * Used by Spring Security for authentication
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if user already exists
     * Needed to prevent duplicates during registration
     */
    boolean existsByUsername(String username);
}
