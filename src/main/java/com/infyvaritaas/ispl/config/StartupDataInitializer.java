package com.infyvaritaas.ispl.config;

import com.infyvaritaas.ispl.domain.User;
import com.infyvaritaas.ispl.domain.UserRole;
import com.infyvaritaas.ispl.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class StartupDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(StartupDataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StartupDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Ensure admin account exists with the credentials requested by the user
        String adminUsername = "admin";
        String adminPasswordPlain = "Admin@123";

        userRepository.findByUsername(adminUsername).ifPresentOrElse(u -> {
            // Ensure admin role and password are set to configured defaults
            boolean changed = false;
            if (u.getRole() != UserRole.ADMIN) {
                u.setRole(UserRole.ADMIN);
                changed = true;
            }
            if (u.isDeleted()) {
                u.setDeleted(false);
                changed = true;
            }
            // Overwrite admin password to the configured default so the operator can rely on the known credential
            u.setPassword(passwordEncoder.encode(adminPasswordPlain));
            changed = true;
            if (changed) {
                userRepository.save(u);
                log.info("Ensured admin account '{}' exists and password/role are set.", adminUsername);
            } else {
                log.info("Admin user already present and valid: {}", adminUsername);
            }
        }, () -> {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail("admin@ispl.local");
            admin.setPassword(passwordEncoder.encode(adminPasswordPlain));
            admin.setRole(UserRole.ADMIN);
            admin.setDeleted(false);
            userRepository.save(admin);
            log.info("Created admin user '{}' with configured default password", adminUsername);
        });

        // Ensure a default user exists for convenience
        String userName = "user";
        String userPassword = "User@123";
        userRepository.findByUsername(userName).ifPresentOrElse(u -> {
            if (u.isDeleted()) {
                u.setDeleted(false);
                userRepository.save(u);
                log.info("Re-enabled existing user: {}", userName);
            } else {
                log.info("User already present: {}", userName);
            }
        }, () -> {
            User user = new User();
            user.setUsername(userName);
            user.setEmail("user@ispl.local");
            user.setPassword(passwordEncoder.encode(userPassword));
            user.setRole(UserRole.USER);
            user.setDeleted(false);
            userRepository.save(user);
            log.info("Created default user '{}'", userName);
        });
    }
}
