package dev.adrianlorenzo.crmservice.controllers;

import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.resourceExceptions.FileNotAnImageException;
import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.resourceExceptions.UsernameUsedException;

public class RestPreconditions {
    public static <T> T checkNotNull(T resource) throws ResourceNotFoundException {
        if (resource == null) {
            throw new ResourceNotFoundException();
        }

        return resource;
    }

    public static Customer checkIfValidCustomer(Customer customer) throws InvalidResourceException {
        if (customer == null) {
            throw new InvalidResourceException();
        }
        if (customer.getName() == null ||
                customer.getSurname() == null) {
            throw new InvalidResourceException();
        }

        return customer;
    }

    public static User checkIfValidUser(User user) throws InvalidResourceException {
        if (user == null) {
            throw new InvalidResourceException();
        }
        if (user.getUsername() == null ||
                user.getPassword() == null) {
            throw new InvalidResourceException();
        }

        return user;
    }

    public static void checkIfImage(String contentType) throws FileNotAnImageException {
        if (!contentType.startsWith("image/")) {
            throw new FileNotAnImageException();
        }
    }

    public static void checkIfUsernameIsUsed(User user) throws UsernameUsedException {
        if(user != null){
            throw new UsernameUsedException();
        }
    }
}

