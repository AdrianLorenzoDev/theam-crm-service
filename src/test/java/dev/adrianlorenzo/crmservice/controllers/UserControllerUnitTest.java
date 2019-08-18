package dev.adrianlorenzo.crmservice.controllers;

import dev.adrianlorenzo.crmservice.TestUtils;
import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @Before
    public void initializeResources() {
        user = TestUtils.getMockUser(1L);
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    @DisplayName("GET All Users")
    public void getAllUsers() throws Exception {
        List<User> all = Collections.singletonList(user);
        given(userService.findAll()).willReturn(all);

        mockMvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    @DisplayName("GET User by username and found")
    public void getUserByUsername_andFound() throws Exception, ResourceNotFoundException {
        given(userService.findByUsername(user.getUsername())).willReturn(user);
        mockMvc.perform(get("/api/users/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())));

    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    @DisplayName("GET User by username and not found")
    public void getUserByUsername_andNotFound() throws Exception, ResourceNotFoundException {
        given(userService.findByUsername(user.getUsername())).willReturn(null);
        mockMvc.perform(get("/api/users/" + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    @DisplayName("Create valid user")
    public void createValidUser() throws Exception {
        given(userService.create(any(User.class))).willReturn(1L);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(user).getBytes()))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @WithMockUser(value = "admin", authorities = "ADMIN")
    @Test
    @DisplayName("Create invalid user")
    public void createInvalidUser() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(null).getBytes()))
                .andExpect(status().isBadRequest());
    }


}
