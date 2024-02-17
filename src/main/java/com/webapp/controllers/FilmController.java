package com.webapp.controllers;

import com.webapp.dto.FilmDto;
import com.webapp.model.FilmEntity;
import com.webapp.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/rutube.ru")
public class FilmController {
    @Autowired
    private FilmService filmService;
    @GetMapping("/all")
    public List<FilmEntity> findAllFilm(){
        return filmService.getAllFilm();
    }

    @PostMapping("/video")
    public String findFilm(@RequestBody FilmDto data){
        return filmService.getFilm(data.getFilmId(), data.getUserId());
    }

}
