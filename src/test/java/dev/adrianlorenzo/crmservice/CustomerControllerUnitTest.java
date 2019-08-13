package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.controller.CustomerController;
import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {
    //TODO
    // - Add testing cases for update and delete

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public CustomerService customerService;

    private Customer customer1;
    private Customer customer2;

    @Before
    public void initializeResources(){
        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setCreatedBy("Adrian");
        customer1.setModifiedBy("Adrian");
        customer1.setImage("an_image");
        customer1.setName("citlalli");
        customer1.setSurname("mateo");

        customer2 = new Customer();
        customer2.setId(2L);
        customer2.setCreatedBy("Adrian2");
        customer2.setModifiedBy("Adrian2");
        customer2.setImage("an_image2");
        customer2.setName("sora");
        customer2.setSurname("lorenzo");

    }

    @Test
    @DisplayName("GET All Customers")
    public void getAllCustomers() throws Exception {
        List<Customer> all = Arrays.asList(customer1);
        given(customerService.findAll()).willReturn(all);

        mockMvc.perform(get("/api/customers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(customer1.getName())))
                .andExpect(jsonPath("$[0].id", is(customer1.getId().intValue())));
    }

    @Test
    @DisplayName("GET Customer by Id")
    public void getCustomerById() throws Exception {
        given(customerService.findById(customer1.getId())).willReturn(customer1);
        mockMvc.perform(get("/api/customers/"+ customer1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(customer1.getName())))
                .andExpect(jsonPath("$.id", is(customer1.getId().intValue())));

        given(customerService.findById(3L)).willReturn(null);
        mockMvc.perform(get("/api/customers/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Create customer")
    public void createCustomer() throws Exception {
        given(customerService.create(customer1)).willReturn(1L);
        mockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.toJson(customer1).getBytes()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$", is(customer1.getId().intValue())));
    }
}
