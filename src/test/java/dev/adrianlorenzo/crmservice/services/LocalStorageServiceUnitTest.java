package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.properties.StorageProperties;
import dev.adrianlorenzo.crmservice.resourceExceptions.StorageException;
import dev.adrianlorenzo.crmservice.services.LocalStorageService;
import dev.adrianlorenzo.crmservice.services.StorageService;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@Ignore
public class LocalStorageServiceUnitTest {
    //TODO
    // - Fix StorageProperties conflict
    /**
     * Tests not working due to conflict with StorageProperties bean
     * and storageService autowiring. Currently looking for documentation
     * and finding a fix.
     */

    @TestConfiguration
    static class MockStorageServiceTestContextConfiguration {
        @Bean
        public StorageService storageService(StorageProperties storageProperties)
                throws StorageException {
            return new LocalStorageService(storageProperties);
        }
    }

    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageProperties storageProperties;

    @Before
    public void initializeResources() {

    }

    @Test
    public void mockTest() {

    }
}
