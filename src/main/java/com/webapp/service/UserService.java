package com.webapp.service;

import com.webapp.auth.AuthenticationRequest;
import com.webapp.auth.RegisterRequest;
import com.webapp.exceptioin.NotFoundException;
import com.webapp.exceptioin.UserExistException;
import com.webapp.model.FilmEntity;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
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
        UserEntity userEntity = userRepository.findUserByEmail(registerRequest.getEmail());
        if (userEntity == null) {
            UserEntity newUser = new UserEntity();
            newUser.setFirstname(registerRequest.getFirstname());
            newUser.setLastname(registerRequest.getLastname());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPassword(registerRequest.getPassword());
            userRepository.save(newUser);
            return newUser;
        } else {
            throw new UserExistException("User already exist");
        }
    }

    public UserEntity authenticate(AuthenticationRequest authenticationRequest){
        UserEntity userEntity = userRepository.findUserByEmail(authenticationRequest.getEmail());
        if (userEntity == null) {
            throw new NotFoundException("Такого пользователя не существует!");
        }
        if(!userEntity.getPassword().equals(authenticationRequest.getPassword())){
            throw new NotFoundException("Пароли не совпадают");
        }
        return null;
    }

    public void updateSubscriptionEndDate(Long id){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserEntity user = userRepository.findUserById(id);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DATE,30);
        timestamp.setTime(calendar.getTime().getTime());

        user.setSubscriptionEndDate(timestamp);
        userRepository.save(user);
    }

}
