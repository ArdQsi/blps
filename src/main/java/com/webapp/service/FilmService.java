package com.webapp.service;

import com.webapp.exceptioin.NotFoundException;
import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    @Autowired
    private UserService userService;
    @Autowired
    private FilmRepository filmRepository;

    public List<FilmEntity> getAllFilm() {
        return filmRepository.findAll();
    }

    public String getFilm(Long filmId, Long userId) {
        FilmEntity film = filmRepository.findFilmById(filmId);
        if (film == null) {
            new NotFoundException("Фильм не существует!");
        }
        if (film.hasSubscription()){
            //проверить есть ли у юзера подписка
            //заплатить за подписку
        }
        userService.addFilmToHistory(filmId, userId);

        return film.getToken();
    }

    public List<FilmEntity> findFilmByGenre(GenreEntity genre) {
        return filmRepository.findFilmByGenre(genre);
    }

    public List<FilmEntity> findFilmByYear(String year) {
        return filmRepository.findFilmByYear(year);
    }
}
