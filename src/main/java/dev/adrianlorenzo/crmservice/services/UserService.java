package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findByUsername(String username);

    Long create(User user);

    void update(User user);

    void delete(User user);

    User findById(Long id);
}
