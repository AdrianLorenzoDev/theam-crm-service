package dev.adrianlorenzo.crmservice.controllers;

import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.resourceExceptions.UsernameUsedException;
import dev.adrianlorenzo.crmservice.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    static final String BASE_URL = "/api/users";
    private final UserService service;
    private final PasswordEncoder encoder;

    public UserController(UserService service, PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{username}")
    public User findUserByUsername(@PathVariable("username") String username) throws ResourceNotFoundException {
        return RestPreconditions.checkNotNull(service.findByUsername(username));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestBody User user) throws InvalidResourceException, UsernameUsedException {
        user.setPassword(encoder.encode(user.getPassword()));
        RestPreconditions.checkIfUsernameIsUsed(service.findByUsername(user.getUsername()));
        user.setDeleted(false);
        return service.create(RestPreconditions.checkIfValidUser(user));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody User user)
            throws InvalidResourceException, ResourceNotFoundException, UsernameUsedException {
        RestPreconditions.checkIfValidUser(user);
        RestPreconditions.checkNotNull(service.findById(user.getId()));
        RestPreconditions.checkIfUsernameIsUsed(service.findByUsername(user.getUsername()));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setDeleted(false);
        service.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
        service.delete(RestPreconditions.checkNotNull(service.findById(id)));
    }
}
