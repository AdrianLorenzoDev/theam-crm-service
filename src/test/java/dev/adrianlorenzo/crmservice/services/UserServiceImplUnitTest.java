package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.TestUtils;
import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.repositories.UserRepository;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;

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
public class UserServiceImplUnitTest {

    @TestConfiguration
    static class MockUserServiceTestContextConfiguration {
        @Bean
        public UserService userService(UserRepository userRepository) {
            return new UserServiceImpl(userRepository);
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @Before
    public void initializeResources() {
        user = TestUtils.getMockUser(1L);
    }

    @Test
    public void findAll() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<User> found = userService.findAll();
        assertThat(found.get(0), is(user));
    }

    @Test
    public void findById() {
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.ofNullable(user));
        User found = userService.findById(user.getId());
        assertThat(found, is(user));
    }

    @Test
    public void findByUsernameAndFound() throws ResourceNotFoundException {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        User found = userService.findByUsername(user.getUsername());
        assertThat(found, is(user));
    }
}