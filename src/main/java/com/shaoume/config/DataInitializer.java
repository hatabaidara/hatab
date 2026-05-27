package com.shaoume.config;
import com.shaoume.entity.User;
import com.shaoume.entity.enums.Role;
import com.shaoume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component @RequiredArgsConstructor @Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) {
        if(!userRepository.existsByEmail("admin@shaoume.com")) {
            userRepository.save(User.builder()
                .firstName("Super").lastName("Admin")
                .email("admin@shaoume.com")
                .password(passwordEncoder.encode("Admin@2024!"))
                .role(Role.ADMIN).enabled(true).emailVerified(true).build());
            log.info("Admin créé => admin@shaoume.com / Admin@2024!");
        }
    }
}
