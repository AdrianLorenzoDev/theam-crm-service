package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.TestUtils;
import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.repositories.CustomerRepository;
import dev.adrianlorenzo.crmservice.services.CustomerService;
import dev.adrianlorenzo.crmservice.services.CustomerServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerServiceImplUnitTest {

    @TestConfiguration
    static class MockCustomerServiceTestContextConfiguration {
        @Bean
        public CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerServiceImpl(customerRepository);
        }
    }

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    private Customer customer1;

    @Before
    public void initializeResources() {
        customer1 = TestUtils.getMockCustomer(1L);
    }

    @Test
    public void findAll() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer1));
        List<Customer> found = customerService.findAll();
        assertThat(found.get(0), is(customer1));
    }

    @Test
    public void findById() {
        when(customerRepository.findById(customer1.getId())).thenReturn(java.util.Optional.ofNullable(customer1));
        Customer found = customerService.findById(customer1.getId());
        assertThat(found.getId(), is(customer1.getId()));
    }

    @Test
    public void create(){
        when(customerRepository.saveAndFlush(customer1)).thenReturn(customer1);
        Long id = customerService.create(customer1);
        assertThat(id, is(customer1.getId()));
    }

}
