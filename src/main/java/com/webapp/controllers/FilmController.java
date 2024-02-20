package com.webapp.controllers;

import com.webapp.dto.*;
import com.webapp.model.FilmEntity;
import com.webapp.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rutube.ru")
public class FilmController {

    private final FilmService filmService;
    @GetMapping("/movies")
    public List<FilmDto> findAllFilm(){
        return filmService.getAllFilm();
    }

    @PostMapping("/video/{token}")
    public MessageDto getFilm(@PathVariable String token, @RequestBody RequestVideoDto requestVideoDto){
        return filmService.getFilm(token, requestVideoDto.getUserId());
    }

    @DeleteMapping("/video/{token}")
    public MessageDto deleteFilm(@PathVariable String token){
        return filmService.deleteFilm(token);
    }

    @PostMapping("/movies")
    public MessageDto addFilm(@RequestBody RequestFilmAddDto requestFilmAddDto) {
        return filmService.addFilm(requestFilmAddDto);
    }

    @PostMapping("/genres")
    public MessageDto addGenre(@RequestBody GenreDto genreDto) {
        return filmService.addGenre(genreDto);
    }

}
