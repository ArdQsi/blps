package com.webapp.service;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import com.webapp.auth.AuthenticationRequest;
import com.webapp.auth.RegisterRequest;
import com.webapp.dto.MessageDto;
import com.webapp.exceptioin.ResourceNotAllowedException;
import com.webapp.exceptioin.ResourceNotFoundException;
import com.webapp.exceptioin.ResourceAlreadyExistsException;
import com.webapp.model.FilmEntity;
import com.webapp.model.Role;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import javax.transaction.SystemException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FilmRepository filmRepository;

    @Transactional
    public MessageDto updateSubscription(Long userId) {

            // Выполнение вашей логики, работающей в пределах транзакции
            UserEntity user = userRepository.findUserById(userId);

            if (user.getBalance() <= 0) {
                throw new ResourceNotFoundException("Balance is empty!");
            }
            if (user.getBalance() <= 300) {
                throw new ResourceNotFoundException("Lack of founds to pay!");
            }

            user.setBalance(user.getBalance() - 300);
            userRepository.save(user);
            updateSubscriptionEndDate(user.getId());
            return new MessageDto("Subscription extended");
    }


    public void updateBalance(UserEntity user, Long balance){
        user.setBalance(balance);
        userRepository.save(user);
    }

    public void addFilmToHistory(Long filmId, Long userId) {
        UserEntity user = userRepository.findUserById(userId);
        FilmEntity film = filmRepository.findFilmById(filmId);

        if(user.getUserFilm().stream().noneMatch(f-> Objects.equals(f.getId(), film.getId()))){
            user.getUserFilm().add(film);
            userRepository.save(user);
        }
    }

    public MessageDto register(RegisterRequest registerRequest){
        UserEntity userEntity = userRepository.findUserByEmail(registerRequest.getEmail());
        if (userEntity == null) {
            UserEntity newUser = new UserEntity();
            newUser.setFirstname(registerRequest.getFirstname());
            newUser.setLastname(registerRequest.getLastname());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPassword(registerRequest.getPassword());
            newUser.setRole(Role.USER);
            userRepository.save(newUser);
            return new MessageDto("Successfully registered");
        } else {
            throw new ResourceAlreadyExistsException("User already exist");
        }
    }

    public MessageDto authenticate(AuthenticationRequest authenticationRequest){
        UserEntity userEntity = userRepository.findUserByEmail(authenticationRequest.getEmail());
        if (userEntity == null) {
            throw new ResourceNotFoundException("This user does not exist");
        }
        if(!userEntity.getPassword().equals(authenticationRequest.getPassword())){
            throw new ResourceNotFoundException("Incorrect password");
        }
        return new MessageDto("OK");
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

    public MessageDto addModerator(Long id){
        UserEntity userEntity = userRepository.findUserById(id);
        if (userEntity == null)
            throw new ResourceNotFoundException("This user does not exist");
        if (userEntity.getRole() == Role.MODERATOR) {
            throw new ResourceNotAllowedException("The user already has the user moderator");
        }
        userEntity.setRole(Role.MODERATOR);
        userRepository.save(userEntity);
        return new MessageDto("Moderator added successfully");
    }

    public MessageDto removeModerator(Long id){
        UserEntity userEntity = userRepository.findUserById(id);
        if (userEntity == null)
            throw new ResourceNotFoundException("This user does not exist");
        if (userEntity.getRole() == Role.USER) {
            throw new ResourceNotAllowedException("The user already has the user role");
        }
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);
        return new MessageDto("Moderator successfully removed");
    }
}
