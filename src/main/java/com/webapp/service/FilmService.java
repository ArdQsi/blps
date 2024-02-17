package com.webapp.service;

import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import com.webapp.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    public List<FilmEntity> getAllFilm() {
        return filmRepository.findAll();
    }

    public Optional<FilmEntity> getFilm(Long filmId) {
        return filmRepository.findById(filmId);
    }

    public List<FilmEntity> findFilmByGenre(GenreEntity genre) {
        return filmRepository.findFilmByGenre(genre);
    }

    public List<FilmEntity> findFilmByYear(String year) {
        return filmRepository.findFilmByYear(year);
    }
}
