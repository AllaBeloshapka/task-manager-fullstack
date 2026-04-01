package com.example.taskmanagerapi.service;

import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor injection of UserRepository
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Find user in database by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert our User entity into Spring Security User
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()) // set username
                .password(user.getPassword())     // set encrypted password
                .roles("USER")                   // assign role (temporary hardcoded)
                .build();
    }
}
