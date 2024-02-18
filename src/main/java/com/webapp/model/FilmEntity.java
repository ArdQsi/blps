package com.webapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="film")
public class FilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String year;
    private String description;
    private boolean subscription;
    private String token;

    @ManyToMany
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name="film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<GenreEntity> genre = new HashSet<GenreEntity>();

    @ManyToMany
    @JoinTable(
            name = "user_history",
            joinColumns = @JoinColumn(name="film_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<UserEntity> filmUser = new HashSet<UserEntity>();

    public boolean hasSubscription(){
        return subscription;
    }

}
