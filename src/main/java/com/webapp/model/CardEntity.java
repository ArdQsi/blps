package com.webapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="card")
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String number;
    private int month;
    private int year;
    private String name;
    private String surname;

    @OneToMany(mappedBy = "card")
    private List<UserEntity> users;

}
