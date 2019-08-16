package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findByUsername(String username) throws ResourceNotFoundException;

    Long create(User user);

    void update(User user) throws ResourceNotFoundException;

    void delete(User user) throws ResourceNotFoundException;

    User findById(Long id);
}
