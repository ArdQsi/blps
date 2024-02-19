package com.webapp.controllers;

import com.webapp.dto.CardDto;
import com.webapp.exceptioin.ResourceNotFoundException;
import com.webapp.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        catch (ResourceNotFoundException e){
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cardDto);
    }
}
