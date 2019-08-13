package dev.adrianlorenzo.crmservice.controller;

import dev.adrianlorenzo.crmservice.controller.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.controller.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.model.Customer;

public class RestPreconditions {
    public static Customer checkNotNull(Customer resource) throws ResourceNotFoundException {
        if (resource == null){
            throw new ResourceNotFoundException();
        }

        return resource;
    }

    public static Customer checkIfValid(Customer customer) throws InvalidResourceException {
        if (customer == null) {
            throw new InvalidResourceException();
        }
        if (customer.getId() == null ||
                customer.getName() == null ||
                customer.getSurname() == null){
            throw new InvalidResourceException();
        }

        return customer;
    }

}

