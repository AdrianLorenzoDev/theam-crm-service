package dev.adrianlorenzo.crmservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.adrianlorenzo.crmservice.model.Customer;

public class TestUtils {
    public static String toJson(Object resource){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(resource);
    }

    public static Customer getMockCustomer(Long id){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setCreatedBy("Adrian");
        customer.setModifiedBy("Adrian");
        customer.setImage("an_image");
        customer.setName("citlalli");
        customer.setSurname("mateo");

        return customer;
    }
}
