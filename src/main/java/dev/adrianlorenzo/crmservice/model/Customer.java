package dev.adrianlorenzo.crmservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    private String image;

    /**
    private String modifiedBy;
    @Column(updatable = false)
    private String createdBy;
    */

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "modifiedBy_username", referencedColumnName = "username")
    private User modifiedBy;

    @Column(name = "modifiedBy_username", insertable = false, updatable = false)
    private String modifiedByName;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "createdBy_username", updatable = false, referencedColumnName = "username")
    private User createdBy;

    @Column(name = "createdBy_username", insertable = false, updatable = false)
    private String createdByName;
}
