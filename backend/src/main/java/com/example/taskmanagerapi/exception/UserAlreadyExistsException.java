package com.example.taskmanagerapi.exception;

/**
 * Thrown when trying to register a user with an existing username.
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}