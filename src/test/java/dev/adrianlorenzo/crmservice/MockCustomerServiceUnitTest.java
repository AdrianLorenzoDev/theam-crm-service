package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.repositories.CustomerRepository;
import dev.adrianlorenzo.crmservice.services.CustomerService;
import dev.adrianlorenzo.crmservice.services.MockCustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MockCustomerServiceUnitTest {

    @TestConfiguration
    static class MockCustomerServiceTestContextConfiguration {
        @Bean
        public CustomerService customerService() {
            return new MockCustomerService();
        }
    }

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    private Customer customer1;


    @Before
    public void initializeResources(){
        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setCreatedBy("Adrian");
        customer1.setModifiedBy("Adrian");
        customer1.setImage("an_image");
        customer1.setName("citlalli");
        customer1.setSurname("mateo");

    }

    @Test
    public void mockTest(){
        when(customerRepository.findById(customer1.getId())).thenReturn(java.util.Optional.ofNullable(customer1));
        Customer found = customerService.findById(customer1.getId());
        assertThat(found.getId(), is(customer1.getId()));
    }

}
