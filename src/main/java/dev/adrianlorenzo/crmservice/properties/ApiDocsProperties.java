package dev.adrianlorenzo.crmservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("api")
public class ApiDocsProperties {
    private String title;
    private String description;
    private String version;
    private String name;
    private String webpage;
    private String email;
    private String termsOfService;
    private String license;
    private String licenseUrl;
}
