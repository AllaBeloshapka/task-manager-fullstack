package com.example.taskmanagerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.*;

@Configuration
@EnableMethodSecurity // enables @PreAuthorize later
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/register/**", "/error").permitAll() // allow registration
                        .anyRequest().authenticated() // everything else требует логина
                )
                .httpBasic(Customizer.withDefaults()); // enable Basic Auth

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // password hashing
    }
}
