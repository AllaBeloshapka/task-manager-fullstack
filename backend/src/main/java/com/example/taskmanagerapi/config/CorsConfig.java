package com.example.taskmanagerapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // Registers a global CORS configuration for the application
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            // Configure CORS mappings for all endpoints
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")

                        // Allow requests from any origin
                        // In production this should be restricted to trusted domains
                        .allowedOrigins("*")

                        // Allow all HTTP methods such as GET, POST, PUT, DELETE
                        .allowedMethods("*")

                        // Allow all request headers
                        .allowedHeaders("*");
            }
        };
    }
}
