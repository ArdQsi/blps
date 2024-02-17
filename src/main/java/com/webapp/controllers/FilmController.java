package com.webapp.controllers;

import com.webapp.model.FilmEntity;
import com.webapp.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/film")
public class FilmController {
    @Autowired
    private FilmService filmService;
    @GetMapping("/all")
    public List<FilmEntity> findAllFilm(){
        return filmService.getAllFilm();
    }

    @PostMapping("/select")
    public Optional<FilmEntity> findFilm(@RequestBody Long id){
        return filmService.getFilm(id);
    }

}
