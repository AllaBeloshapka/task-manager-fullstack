package com.example.taskmanagerapi.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    private Boolean used;

    @ManyToOne
    private User user;
}
