package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.model.Customer;
import dev.adrianlorenzo.crmservice.services.StorageService;

import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MockStorageServiceUnitTest {
    /**
     * No customs SQL queries added. Tests not needed.
     */

    @Autowired
    private StorageService storageService;

    private Customer customer1;

    @Before
    public void initializeResources() {
        customer1 = TestUtils.getMockCustomer(1L);
    }
}
