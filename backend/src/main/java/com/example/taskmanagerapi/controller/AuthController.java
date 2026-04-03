package com.example.taskmanagerapi.controller;

import com.example.taskmanagerapi.dto.RegisterRequest;
import com.example.taskmanagerapi.dto.UserResponse;
import com.example.taskmanagerapi.entity.EmailVerificationToken;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.repository.EmailVerificationTokenRepository;
import com.example.taskmanagerapi.repository.UserRepository;
import com.example.taskmanagerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // Service layer dependency for user-related business logic
    private final UserService userService;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;

    @GetMapping("/test")
    public String test() {

        return "Auth controller works";
    }

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

    /*  Берёт токен из URL
        Ищет его в базе
        Проверяет:
        не использован
        не истёк
        Берёт пользователя
        Включает его (enabled = true)
        Помечает токен как использованный
        Сохраняет всё  */

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {

        EmailVerificationToken verificationToken = emailVerificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (verificationToken.getUsed()) {
            throw new RuntimeException("Token already used");
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);

        verificationToken.setUsed(true);

        userRepository.save(user);
        emailVerificationTokenRepository.save(verificationToken);

        return "Email verified successfully";
    }

    @PostMapping("/resend-verify")
    public String resendVerification(@RequestParam String email) {

        userService.resendVerification(email);

        return "Verification email resent";
    }

}