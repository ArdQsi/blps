package com.webapp.controllers;

import com.webapp.dto.CardDto;
import com.webapp.dto.FilmDto;
import com.webapp.exceptioin.NotFoundException;
import com.webapp.model.FilmEntity;
import com.webapp.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rutube.ru")
public class PaymentController {
    private final CardService cardService;

    @PostMapping("/pay")
    public ResponseEntity<CardDto> saveCard(@RequestBody CardDto cardDto){
        try{
            cardService.saveCard(cardDto);
        }
        catch (NotFoundException e){
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cardDto);
    }
}
