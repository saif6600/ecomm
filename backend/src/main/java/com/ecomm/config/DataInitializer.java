package com.ecomm.config;

import com.ecomm.entity.Role;
import com.ecomm.entity.User;
import com.ecomm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> userRepository.findByEmail("admin@ecomm.com").orElseGet(() ->
                userRepository.save(User.builder()
                        .name("Admin")
                        .email("admin@ecomm.com")
                        .password(passwordEncoder.encode("Admin@123"))
                        .role(Role.ROLE_ADMIN)
                        .build())
        );
    }
}
