package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        StorageProperties.class
})
public class CrmserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmserviceApplication.class, args);
    }

}
