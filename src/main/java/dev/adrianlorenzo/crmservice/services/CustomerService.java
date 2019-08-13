package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer findById(Long id);

    Long create(Customer customer);

    void update(Customer customer);

    void delete(Customer customer);

}
