package com.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+@{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,4}$")
    private String email;
    private String password;
}

