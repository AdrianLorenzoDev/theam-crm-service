package dev.adrianlorenzo.crmservice.model;

import lombok.Data;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Entity
@SQLDelete(sql="UPDATE user SET deleted = true WHERE id = ?")
@Where(clause="deleted = false")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @NotEmpty
    private String password;

    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private Boolean deleted;
}
