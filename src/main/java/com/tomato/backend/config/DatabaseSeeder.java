package com.tomato.backend.config;

import com.tomato.backend.entity.User;
import com.tomato.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed admin user if not exists
        String adminEmail = "admin@tomato.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .name("Tomato Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("adminPassword123"))
                    .phone("1234567890")
                    .role("ADMIN")
                    .build();
            userRepository.save(admin);
            System.out.println("Seeded default admin user: " + adminEmail);
        }
    }
}
