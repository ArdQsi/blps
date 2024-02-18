package com.webapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_user")
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstname;
    private String lastname;
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+@{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,4}$")
    private String email;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CardEntity card;
}

