package com.webapp.controllers;

import com.webapp.dto.FilmDto;
import com.webapp.dto.FilmUserDto;
import com.webapp.dto.GenreDto;
import com.webapp.model.FilmEntity;
import com.webapp.model.GenreEntity;
import com.webapp.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rutube.ru")
public class FilmController {

    private final FilmService filmService;
    @GetMapping("/all")
    public List<FilmEntity> findAllFilm(){
        return filmService.getAllFilm();
    }

    @PostMapping("/video")
    public String findFilm(@RequestBody FilmUserDto data){
        return filmService.getFilm(data.getFilmId(), data.getUserId());
    }

    @PostMapping("/addfilm")
    public List<FilmEntity> addFilm(@RequestBody FilmDto filmDto) {
        return filmService.addFilm(filmDto);
    }

    @PostMapping("/addgenre")
    public List<GenreEntity> addFilm(@RequestBody GenreDto genreDto) {
        return filmService.addGenre(genreDto);
    }

}
