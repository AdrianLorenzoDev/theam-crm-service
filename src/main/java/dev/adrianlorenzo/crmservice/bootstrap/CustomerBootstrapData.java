package dev.adrianlorenzo.crmservice.bootstrap;

import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomerBootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public CustomerBootstrapData(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Customer customer2 = new Customer();
        customer2.setCreatedBy("Adrian2");
        customer2.setModifiedBy("Adrian2");
        customer2.setImage("an_image2");
        customer2.setName("sora");
        customer2.setSurname("lorenzo");

        customerRepository.save(customer2);
    }
}
