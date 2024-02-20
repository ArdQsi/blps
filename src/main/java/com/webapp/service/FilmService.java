package com.webapp.service;

import com.webapp.dto.FilmDto;
import com.webapp.dto.GenreDto;
import com.webapp.dto.MessageDto;
import com.webapp.exceptioin.ResourceAlreadyExistsException;
import com.webapp.exceptioin.ResourceNotAllowedException;
import com.webapp.exceptioin.ResourceNotFoundException;
import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import com.webapp.model.UserEntity;
import com.webapp.repository.FilmRepository;
import com.webapp.repository.GenreRepository;
import com.webapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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

//    public MessageDto updateFilm(FilmDto filmDto){
//        FilmEntity filmEntity = filmRepository.findFilmByToken(filmDto.getToken());
//        if(filmEntity ==null){
//            throw new ResourceNotFoundException("Film not found");
//        }
//
//    }

    public MessageDto deleteFilm(String token){
        FilmEntity filmEntity = filmRepository.findFilmByToken(token);
        if(filmEntity ==null){
            throw new ResourceNotFoundException("Movie not found");
        }
        filmRepository.deleteAllByToken(token);
        return new MessageDto("Movie was deleted");
    }

    public MessageDto getFilm(String token, Long userId) {
        FilmEntity film = filmRepository.findFilmByToken(token);
        UserEntity user = userRepository.findUserById(userId);
        System.out.println(userId);
        System.out.println(user);
        if (film == null) {
            throw new ResourceNotFoundException("The movie doesn't exist");
        }
        if (film.hasSubscription()){
            if(user.getSubscriptionEndDate() != null && user.getSubscriptionEndDate().after(new Timestamp(System.currentTimeMillis()))) {
                userService.addFilmToHistory(film.getId(), userId);
                return new MessageDto("Watching a movie");
            }
            throw new ResourceNotAllowedException("Access is denied");
        }
        userService.addFilmToHistory(film.getId(), userId);
        return new MessageDto("Watching a movie");
    }


    public List<FilmEntity> findFilmByYear(String year) {
        return filmRepository.findFilmByYear(year);
    }

    public FilmEntity findFilmByName(String name) {
        return filmRepository.findFilmByName(name);
    }

    public MessageDto addFilm(FilmDto filmDto) {
        FilmEntity filmEntity = filmRepository.findFilmByName(filmDto.getName());
        if (filmEntity != null) {
            throw new ResourceAlreadyExistsException("This movie already exists");
        }
        FilmEntity newFilmEntity = new FilmEntity();
        GenreEntity genreEntity = new GenreEntity();
        Set<String> genreNames = filmDto.getGenreNames();

        for(String genreName : genreNames){
            genreEntity = genreRepository.findByName(genreName);
            if (genreEntity==null) {
                throw new ResourceNotFoundException("Genre not found");
            }
            newFilmEntity.getGenres().add(genreEntity);
            genreEntity.getFilms().add(newFilmEntity);
        }

        newFilmEntity.setName(filmDto.getName());
        newFilmEntity.setYear(filmDto.getYear());
        newFilmEntity.setSubscription(filmDto.getSubscription());
        newFilmEntity.setDescription(filmDto.getDescription());
        newFilmEntity.setToken(filmDto.getToken());

        filmRepository.save(newFilmEntity);
        genreRepository.save(genreEntity);
        return new MessageDto("Movie added successfully");
    }

    public MessageDto addGenre(GenreDto genreDto) {
        GenreEntity genreEntity = genreRepository.findByName(genreDto.getName());
        if (genreEntity != null) {
            throw new ResourceAlreadyExistsException("This genre already exists");
        }
        GenreEntity newGenreEntity = new GenreEntity();
        newGenreEntity.setName(genreDto.getName());

        genreRepository.save(newGenreEntity);
        return new MessageDto("Genre added successfully");
    }
}
