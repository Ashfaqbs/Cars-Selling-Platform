package com.ashfaq.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Car_app_users") // Specify the table name
@Data                  // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
//@Builder               // Lombok annotation to implement the Builder pattern
@AllArgsConstructor    // Lombok annotation to generate a constructor with all arguments
@NoArgsConstructor     // Lombok annotation to generate a no-argument constructor
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String role;  // e.g., "USER", "SELLER", "ADMIN"

}
