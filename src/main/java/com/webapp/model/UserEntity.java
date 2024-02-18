package com.webapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



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
    @Email
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp subscriptionEndDate;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<CardEntity> cards;

    @ManyToMany
    @JoinTable(
            name = "user_history",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="film_id")
    )
    private Set<FilmEntity> userFilm = new HashSet<FilmEntity>();

}

