package com.webapp.service;

import com.webapp.dto.FilmDto;
import com.webapp.dto.GenreDto;
import com.webapp.exceptioin.NotFoundException;
import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.GenreRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;


import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final UserService userService;

    public List<FilmEntity> getAllFilm() {
        return filmRepository.findAll();
    }

    public String getFilm(Long filmId, Long userId) {
        FilmEntity film = filmRepository.findFilmById(filmId);
        UserEntity user = userRepository.findUserById(userId);
        if (film == null) {
            new NotFoundException("Фильм не существует!");
        }
        if (film.hasSubscription()){
            if(user.getSubscriptionEndDate() != null && user.getSubscriptionEndDate().after(new Timestamp(System.currentTimeMillis()))) {
                userService.addFilmToHistory(filmId, userId);
                System.out.println("есть актуальная подписка");
                userService.addFilmToHistory(filmId, userId);
                return film.getToken();
            }
            return null;
        }
        userService.addFilmToHistory(filmId, userId);
        return film.getToken();
    }


    public List<FilmEntity> findFilmByYear(String year) {
        return filmRepository.findFilmByYear(year);
    }

    public List<FilmEntity> findFilmByName(String name) {
        return filmRepository.findFilmByName(name);
    }

    public List<FilmEntity> addFilm(FilmDto filmDto) {
        FilmEntity filmEntity = new FilmEntity();
        GenreEntity genreEntity = genreRepository.findByName(filmDto.getGenre());
        filmEntity.setName(filmDto.getName());
        filmEntity.setYear(filmDto.getYear());
        filmEntity.setSubscription(filmDto.getSubscription());
        filmEntity.setDescription(filmDto.getDescription());
        filmEntity.setToken(filmDto.getToken());

        filmEntity.getGenres().add(genreEntity);
        genreEntity.getFilms().add(filmEntity);

        filmRepository.save(filmEntity);
        genreRepository.save(genreEntity);
        return null;
    }

    public List<GenreEntity> addGenre(GenreDto genreDto) {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(genreDto.getName());

        genreRepository.save(genreEntity);
        return null;
    }
}
