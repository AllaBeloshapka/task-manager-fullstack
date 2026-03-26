package com.example.taskmanagerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Task Manager application.
 *
 * Responsibilities:
 * - Bootstraps the Spring Boot application
 * - Triggers component scanning and auto-configuration
 *
 * Note:
 * - @SpringBootApplication includes:
 *   - @Configuration
 *   - @EnableAutoConfiguration
 *   - @ComponentScan
 */
@SpringBootApplication
public class TaskManagerApiApplication {

    /**
     * Main method used to start the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApiApplication.class, args);
    }

}