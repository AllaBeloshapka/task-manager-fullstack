package com.example.taskmanagerapi.dto;

import lombok.Data;

/**
 * DTO for sending user data to the client.
 * Used in responses (never expose password here).
 */
@Data
public class UserResponse {

    /**
     * Unique identifier of the user.
     */
    private Long id;

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Role of the user (e.g. ROLE_USER, ROLE_ADMIN).
     */
    private String role;

    /**
     * Indicates whether the user account is enabled.
     */
    private boolean enabled;
}
