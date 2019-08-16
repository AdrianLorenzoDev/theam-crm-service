package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.model.UserDetailsImpl;
import dev.adrianlorenzo.crmservice.repositories.UserRepository;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }


    @Override
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) throws ResourceNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return user;
    }

    @Override
    public Long create(User user) {
        return repository.saveAndFlush(user).getId();
    }

    @Override
    public void update(User user) throws ResourceNotFoundException {
        findByUsername(user.getUsername());
        repository.save(user);
    }

    @Override
    public void delete(User user) throws ResourceNotFoundException {
        findByUsername(user.getUsername());
        repository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(user);
    }
}
