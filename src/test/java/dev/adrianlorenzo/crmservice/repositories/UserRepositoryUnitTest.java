package dev.adrianlorenzo.crmservice.repositories;

import dev.adrianlorenzo.crmservice.TestUtils;
import dev.adrianlorenzo.crmservice.model.User;

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
public class UserRepositoryUnitTest {
    /**
     * UserRepository autowired by Spring.
     * No custom SQL queries implemented.
     */

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void initializeResources() {
        user = TestUtils.getMockUser(null);
    }

    @Test
    public void mockTest() {
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findById(user.getId()).get();

        assertThat(found.getId(), is(user.getId()));
    }
}
