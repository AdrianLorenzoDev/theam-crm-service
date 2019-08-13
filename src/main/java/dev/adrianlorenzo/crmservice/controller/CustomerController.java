package dev.adrianlorenzo.crmservice.controller;

import dev.adrianlorenzo.crmservice.controller.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.controller.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    static final String BASE_URL = "/api/customers";
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<Customer> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Customer findCustomerById(@PathVariable("id") Long id){
        try {
            return RestPreconditions.checkNotNull(service.findById(id));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Requested customer not found", e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createCustomer(@RequestBody Customer resource) {
        try {
            RestPreconditions.checkIfValid(resource);
            return service.create(resource);
        } catch (InvalidResourceException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid customer supplied in request", e);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody Customer resource) {
        try {
            RestPreconditions.checkIfValid(resource);
            try {
                RestPreconditions.checkNotNull(service.findById(resource.getId()));
                service.update(resource);
            } catch (ResourceNotFoundException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Requested customer not found", e);
            }
        } catch (InvalidResourceException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid customer supplied in request", e);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        try {
            service.delete(
                    RestPreconditions.checkNotNull(service.findById(id))
            );
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Requested customer not found", e);
        }
    }
}
