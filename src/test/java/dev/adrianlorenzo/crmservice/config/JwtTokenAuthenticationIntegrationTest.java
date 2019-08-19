package dev.adrianlorenzo.crmservice.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.adrianlorenzo.crmservice.TestUtils;
import dev.adrianlorenzo.crmservice.model.AuthenticationRequest;
import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.model.UserDetailsImpl;
import dev.adrianlorenzo.crmservice.services.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JwtTokenAuthenticationIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private User user;

    @Before
    public void initializeResources() {
        user = TestUtils.getMockUser(1L);
    }

    @Test
    public void notAllowUnauthenticated() throws Exception {
        mvc.perform(get("/api/customers")).andExpect(status().isForbidden());
    }

    @WithMockUser
    @Ignore("Conflict on filter when authenticating due to test environment bug. Looking for fix")
    @Test
    public void jwtTokenAuthenticated() throws Exception {
        given(userService.loadUserByUsername(user.getUsername())).willReturn(new UserDetailsImpl(user));
        given(userService.findByUsername(user.getUsername())).willReturn(user);
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("user");
        request.setPassword("password");

        MvcResult result = mvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(request).getBytes()))
                .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        JsonObject jsonResponse = new JsonParser().parse(response).getAsJsonObject();
        String token = jsonResponse.get("token").getAsString();


        mvc.perform(get("/api/customers")
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }
}
