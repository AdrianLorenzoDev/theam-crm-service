package dev.adrianlorenzo.crmservice.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="surname", nullable = false)
    private String surname;
    private String image;
    private String modifiedBy;
    @Column(name="createdBy", nullable = false)
    private String createdBy;
}
