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

    /**
     * Username for the new account.
     *
     * Constraints:
     * - must not be blank
     * - length between 3 and 50 characters
     * - allows only letters, digits, and underscore
     *
     * Note:
     * - restrictions help prevent invalid data and simplify further processing
     */
    @NotBlank(message = "Username обязателен")
    @Size(min = 3, max = 50, message = "Username от 3 до 50 символов")
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+$",
            message = "Username может содержать только буквы, цифры и _"
    )
    private String username;

    /**
     * Raw password provided by the user.
     *
     * Constraints:
     * - must not be blank
     * - minimum length is 6 characters
     *
     * Important:
     * - password must be hashed in the service layer before persisting
     * - never return or expose this field in responses
     */
    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Минимальная длина пароля — 6 символов")
    private String password;
}
