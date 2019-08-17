package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.model.Customer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.adrianlorenzo.crmservice.model.User;
import dev.adrianlorenzo.crmservice.model.UserRole;

public class TestUtils {
    public static String toJson(Object resource) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(resource);
    }

    public static Customer getMockCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setImage("an_image");
        customer.setName("citlalli");
        customer.setSurname("mateo");

        return customer;
    }

    public static User getMockUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername("user");
        user.setPassword("password");
        user.setDeleted(false);
        user.setUserRole(UserRole.USER);

        return user;
    }
}
