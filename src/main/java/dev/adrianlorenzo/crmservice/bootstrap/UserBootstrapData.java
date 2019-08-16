package dev.adrianlorenzo.crmservice.bootstrap;

import dev.adrianlorenzo.crmservice.model.UserRole;
import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserBootstrapData implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserBootstrapData(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setUserRole(UserRole.USER);
        user.setDeleted(false);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("4321"));
        admin.setUserRole(UserRole.ADMIN);
        admin.setDeleted(false);

        repository.save(user);
        repository.save(admin);
    }
}
