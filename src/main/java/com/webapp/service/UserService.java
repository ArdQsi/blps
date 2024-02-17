package com.webapp.service;

import com.webapp.model.FilmEntity;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilmRepository filmRepository;

    public void addFilmToHistory(Long filmId, Long userId) {
        UserEntity user = userRepository.findUserById(userId);
        FilmEntity film = filmRepository.findFilmById(filmId);

        if(user.getUserFilm().stream().noneMatch(f-> Objects.equals(f.getId(), film.getId()))){
            user.getUserFilm().add(film);
            userRepository.save(user);
        }
    }
}
