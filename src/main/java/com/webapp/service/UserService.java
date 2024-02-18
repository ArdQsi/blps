package com.webapp.service;

import com.webapp.auth.AuthenticationRequest;
import com.webapp.auth.RegisterRequest;
import com.webapp.exceptioin.NotFoundException;
import com.webapp.model.FilmEntity;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;

    public void addFilmToHistory(Long filmId, Long userId) {
        UserEntity user = userRepository.findUserById(userId);
        FilmEntity film = filmRepository.findFilmById(filmId);

        if(user.getUserFilm().stream().noneMatch(f-> Objects.equals(f.getId(), film.getId()))){
            user.getUserFilm().add(film);
            userRepository.save(user);
        }
    }

    public UserEntity register(RegisterRequest registerRequest){
        System.out.println(registerRequest.getEmail());
        UserEntity userEntity = userRepository.findUserByEmail(registerRequest.getEmail());
        if (userEntity == null) {
            UserEntity newUser = new UserEntity();
            newUser.setFirstname(registerRequest.getFirstname());
            newUser.setLastname(registerRequest.getLastname());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPassword(registerRequest.getPassword());
            userRepository.save(newUser);
        }
        return null;
    }

    public UserEntity authenticate(AuthenticationRequest authenticationRequest){
        UserEntity userEntity = userRepository.findUserByEmail(authenticationRequest.getEmail());
        if (userEntity == null) {
            throw new NotFoundException("Такого пользователя не существует!");
        }
        return null;
    }

}
