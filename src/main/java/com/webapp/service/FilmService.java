package com.webapp.service;

import com.webapp.exceptioin.NotFoundException;
import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
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
                //есть подписка
                userService.addFilmToHistory(filmId, userId);
                System.out.println("есть актуальная подписка");
            }
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
