package com.webapp.service;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import com.webapp.auth.AuthenticationRequest;
import com.webapp.auth.RegisterRequest;
import com.webapp.dto.AuthenticationResponseDto;
import com.webapp.dto.MessageDto;
import com.webapp.exceptioin.ResourceNotAllowedException;
import com.webapp.exceptioin.ResourceNotFoundException;
import com.webapp.exceptioin.ResourceAlreadyExistsException;
import com.webapp.model.FilmEntity;
import com.webapp.model.Role;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.UserRepository;
import com.webapp.repository.UserXmlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${SUBSCRIPTION_PRICE}")
    private int price;
    private final UserRepository userRepository;
    private final UserXmlRepository userXmlRepository;
    private final FilmRepository filmRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BitronixTransactionManager bitronixTransactionManager;

    @Transactional
    public MessageDto updateSubscription(Long userId) {
            UserEntity user = userRepository.findUserById(userId);

            if (user.getBalance() <= 0) {
                throw new ResourceNotFoundException("Balance is empty!");
            }
            if (user.getBalance() <= price) {
                throw new ResourceNotFoundException("Lack of founds to pay!");
            }

            user.setBalance(user.getBalance() - price);
            userRepository.save(user);
            updateSubscriptionEndDate(user.getId());
            return new MessageDto("Subscription extended");
    }

    public void updateBalance(UserEntity user, Long amount) {
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    public void addFilmToHistory(Long filmId, Long userId) {
        UserEntity user = userRepository.findUserById(userId);
        FilmEntity film = filmRepository.findFilmById(filmId);

        if (user.getUserFilm().stream().noneMatch(f -> Objects.equals(f.getId(), film.getId()))) {
            user.getUserFilm().add(film);
            userRepository.save(user);
            userXmlRepository.save(user);
        }
    }

    public AuthenticationResponseDto register(RegisterRequest registerRequest) {
        Optional<UserEntity> userEntity = userRepository.findUserByEmail(registerRequest.getEmail());
        if (!userEntity.isPresent()) {
            UserEntity user = UserEntity.builder()
                    .firstname(registerRequest.getFirstname())
                    .lastname(registerRequest.getLastname())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();
            userRepository.save(user);
            userXmlRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponseDto.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new ResourceAlreadyExistsException("User already exist");
        }
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public void updateSubscriptionEndDate(Long id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserEntity user = userRepository.findUserById(id);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DATE, 30);
        timestamp.setTime(calendar.getTime().getTime());
        user.setSubscriptionEndDate(timestamp.toString());
        userRepository.save(user);
        userXmlRepository.save(user);
    }

    public MessageDto addModerator(Long id) {
        System.out.println("privet");
        UserEntity userEntity = userRepository.findUserById(id);
        System.out.println(userEntity);
        if (userEntity == null)
            throw new ResourceNotFoundException("This user does not exist");
        if (userEntity.getRole() == Role.ROLE_MODERATOR) {
            throw new ResourceNotAllowedException("The user already has the user moderator");
        }
        userEntity.setRole(Role.ROLE_MODERATOR);
        userRepository.save(userEntity);
        userXmlRepository.save(userEntity);
        return new MessageDto("Moderator added successfully");
    }

    public MessageDto removeModerator(Long id) {
        UserEntity userEntity = userRepository.findUserById(id);
        if (userEntity == null)
            throw new ResourceNotFoundException("This user does not exist");
        if (userEntity.getRole() == Role.ROLE_USER) {
            throw new ResourceNotAllowedException("The user already has the user role");
        }
        userEntity.setRole(Role.ROLE_USER);
        userRepository.save(userEntity);
        userXmlRepository.save(userEntity);
        return new MessageDto("Moderator successfully removed");
    }
}
