package com.shaoume.config;
import com.shaoume.entity.User;
import com.shaoume.entity.enums.Role;
import com.shaoume.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
@Component @RequiredArgsConstructor @Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) {
        // Toujours mettre a jour le mot de passe admin au demarrage
        if(!userRepository.existsByEmail("admin@shaoume.com")) {
            userRepository.save(User.builder()
                .firstName("Super").lastName("Admin")
                .email("admin@shaoume.com")
                .password(passwordEncoder.encode("Admin@2024!"))
                .role(Role.ADMIN).enabled(true).emailVerified(true)
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .build());
            log.info("Admin cree => admin@shaoume.com / Admin@2024!");
        } else {
            // Mettre a jour le mot de passe et le role au cas ou
            userRepository.findByEmail("admin@shaoume.com").ifPresent(admin -> {
                admin.setPassword(passwordEncoder.encode("Admin@2024!"));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);
                if(admin.getCreatedAt()==null) admin.setCreatedAt(java.time.LocalDateTime.now());
                if(admin.getUpdatedAt()==null) admin.setUpdatedAt(java.time.LocalDateTime.now());
                userRepository.save(admin);
                log.info("Admin mis a jour => admin@shaoume.com");
            });
        }
        // Promouvoir admin2 si existe
        userRepository.findByEmail("admin2@shaoume.com").ifPresent(u -> {
            u.setRole(Role.ADMIN);
            u.setEnabled(true);
            userRepository.save(u);
            log.info("admin2@shaoume.com promu ADMIN");
        });
    }
}
