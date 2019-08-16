package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.controller.CustomerController;
import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.services.CustomerService;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public CustomerService customerService;

    private Customer customer1;

    @Before
    public void initializeResources() {
        customer1 = TestUtils.getMockCustomer(1L);
    }

    @Test
    @DisplayName("GET All Customers")
    public void getAllCustomers() throws Exception {
        List<Customer> all = Collections.singletonList(customer1);
        given(customerService.findAll()).willReturn(all);

        mockMvc.perform(get("/api/customers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(customer1.getName())));
    }

    @Test
    @DisplayName("GET Customer by Id and found")
    public void getCustomerById_andFound() throws Exception {
        given(customerService.findById(customer1.getId())).willReturn(customer1);
        mockMvc.perform(get("/api/customers/" + customer1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(customer1.getName())))
                .andExpect(jsonPath("$.id", is(customer1.getId().intValue())));

    }

    @Test
    @DisplayName("GET Customer by Id and not found")
    public void getCustomerById_andNotFound() throws Exception {
        given(customerService.findById(3L)).willReturn(null);
        mockMvc.perform(get("/api/customers/" + 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Create valid customer")
    public void createValidCustomer() throws Exception {
        given(customerService.create(customer1)).willReturn(1L);
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(customer1).getBytes()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", is(customer1.getId().intValue())));
    }

    @Test
    @DisplayName("Create invalid customer")
    public void invalidValidCustomer() throws Exception {
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(null).getBytes()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update valid customer")
    public void updateValidCustomer() throws Exception {
        Mockito.doNothing().when(customerService).update(customer1);
        given(customerService.findById(customer1.getId())).willReturn(customer1);
        mockMvc.perform(put("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(customer1).getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update invalid customer")
    public void updateInvalidCustomer() throws Exception {
        given(customerService.findById(null)).willReturn(null);
        mockMvc.perform(put("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(null).getBytes()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update unknown customer")
    public void updateUnknownCustomer() throws Exception {
        given(customerService.findById(customer1.getId())).willReturn(null);
        mockMvc.perform(put("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(customer1).getBytes()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete customer")
    public void deleteCustomer() throws Exception {
        Mockito.doNothing().when(customerService).update(customer1);
        given(customerService.findById(customer1.getId())).willReturn(customer1);
        mockMvc.perform(delete("/api/customers/" + customer1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(null).getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete unknown customer")
    public void deleteUnknownCustomer() throws Exception {
        given(customerService.findById(customer1.getId())).willReturn(null);
        mockMvc.perform(delete("/api/customers/" + customer1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.toJson(null).getBytes()))
                .andExpect(status().isNotFound());
    }

}
