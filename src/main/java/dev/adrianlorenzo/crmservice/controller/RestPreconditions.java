package dev.adrianlorenzo.crmservice.controller;

import dev.adrianlorenzo.crmservice.resourceExceptions.FileNotAnImageException;
import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.model.Customer;

public class RestPreconditions {
    public static Customer checkNotNull(Customer resource) throws ResourceNotFoundException {
        if (resource == null){
            throw new ResourceNotFoundException();
        }

        return resource;
    }

    public static Customer checkIfValidCustomer(Customer customer) throws InvalidResourceException {
        if (customer == null) {
            throw new InvalidResourceException();
        }
        if (customer.getName() == null ||
                customer.getSurname() == null){
            throw new InvalidResourceException();
        }

        return customer;
    }

    public static void checkIfImage(String contentType) throws FileNotAnImageException {
        if (! contentType.startsWith("image/")){
            throw new FileNotAnImageException();
        }
    }
}

