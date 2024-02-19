package com.webapp.repository;

import java.util.*;

import com.webapp.dto.FilmDto;
import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    FilmEntity findFilmByName(String name);

    FilmEntity findFilmById(Long id);
    FilmEntity findFilmByToken(String token);

    List<FilmEntity> findFilmByYear(String year);
}
