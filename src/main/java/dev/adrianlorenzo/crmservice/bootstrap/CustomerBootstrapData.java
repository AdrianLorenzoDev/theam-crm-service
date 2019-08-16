package dev.adrianlorenzo.crmservice.bootstrap;

import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerBootstrapData implements CommandLineRunner {

    private final CustomerRepository repository;

    public CustomerBootstrapData(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        Customer customer2 = new Customer();
        customer2.setImage("an_image2");
        customer2.setName("sora");
        customer2.setSurname("lorenzo");

        repository.save(customer2);
    }
}
