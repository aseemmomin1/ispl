package com.infyvaritaas.ispl.service;

import com.infyvaritaas.ispl.domain.User;
import com.infyvaritaas.ispl.domain.UserRole;
import com.infyvaritaas.ispl.dto.RegisterRequest;
import com.infyvaritaas.ispl.exception.BadRequestException;
import com.infyvaritaas.ispl.repository.UserRepository;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        String username = request.getUsername() == null ? "" : request.getUsername().trim();
        String email = request.getEmail() == null ? "" : request.getEmail().trim();
        String password = request.getPassword() == null ? "" : request.getPassword();

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            throw new BadRequestException("Username, email and password are required.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BadRequestException("Username already exists.");
        }
        if (userRepository.findByEmail(email.toLowerCase(Locale.ROOT)).isPresent()) {
            throw new BadRequestException("Email already exists.");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email.toLowerCase(Locale.ROOT));
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }
}
