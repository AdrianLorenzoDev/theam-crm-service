package dev.adrianlorenzo.crmservice.controllers;

import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.model.UserDetailsImpl;
import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.services.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    static final String BASE_URL = "/api/customers";
    private final CustomerService service;


    public CustomerController(CustomerService service) {
        this.service = service;
    }

    private User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsImpl) auth.getPrincipal()).getUser();
    }

    @GetMapping
    public List<Customer> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Customer findCustomerById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return RestPreconditions.checkNotNull(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCustomer(@RequestBody Customer customer) throws InvalidResourceException {
        customer.setCreatedBy(getAuthenticatedUser());
        return service.create(RestPreconditions.checkIfValidCustomer(customer));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody Customer customer)
            throws InvalidResourceException, ResourceNotFoundException {
        RestPreconditions.checkIfValidCustomer(customer);
        customer.setModifiedBy(getAuthenticatedUser());
        RestPreconditions.checkNotNull(service.findById(customer.getId()));
        service.update(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable("id") Long id) throws ResourceNotFoundException {
        service.delete(RestPreconditions.checkNotNull(service.findById(id)));
    }
}
