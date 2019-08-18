package dev.adrianlorenzo.crmservice.config;

import dev.adrianlorenzo.crmservice.services.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JwtTokenAuthenticationIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void notAllowUnauthenticated() throws Exception {
        mvc.perform(get("/api/customers")).andExpect(status().isForbidden());
    }

    @WithMockUser
    @Ignore("Conflict on filter when authenticating due to test environment bug. Looking for fix")
    @Test
    public void jwtTokenAuthenticated() throws Exception {
        String token = new JwtTokenProvider(userService).getToken("user", "USER");
        assertNotNull(token);
        mvc.perform(get("/api/customers")
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }
}
