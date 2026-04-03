package com.example.taskmanagerapi.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO for user registration request.
 *
 * Contains validated input data required to create a new user.
 * Validation is handled via Jakarta Bean Validation annotations.
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Username обязателен")
    @Size(min = 3, max = 50, message = "Username от 3 до 50 символов")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Username может содержать только буквы, цифры и _"
    )
    private String username; // Теперь проверки для ника на месте

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email; // Теперь @Email проверяет именно почту

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Минимальная длина пароля — 6 символов")
    private String password;
}