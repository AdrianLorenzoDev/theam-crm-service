package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.repositories.CustomerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryUnitTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;

    @Before
    public void initializeResources() {
        customer1 = TestUtils.getMockCustomer(null);
    }

    @Test
    public void mockTest() {
        entityManager.persist(customer1);
        entityManager.flush();

        Customer found = customerRepository.findById(customer1.getId()).get();

        assertThat(found.getId(), is(customer1.getId()));
    }
}
