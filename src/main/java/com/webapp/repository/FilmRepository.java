package com.webapp.repository;

import java.util.*;

import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    List<FilmEntity> findByName(String name);
    List<FilmEntity> findFilmByGenre(GenreEntity genre);

    List<FilmEntity> findFilmByYear(String year);
}
