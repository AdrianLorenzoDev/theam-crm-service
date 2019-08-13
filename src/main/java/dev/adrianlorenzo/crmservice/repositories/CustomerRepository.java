package dev.adrianlorenzo.crmservice.repositories;

import dev.adrianlorenzo.crmservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
