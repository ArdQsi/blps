package com.webapp.controllers;

import com.webapp.dto.CardDto;
import com.webapp.dto.MessageDto;
import com.webapp.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rutube.ru")
public class PaymentController {
    private final CardService cardService;

    @PostMapping("/pay")
    public MessageDto saveCard(@RequestBody CardDto cardDto){
        return cardService.saveCard(cardDto);
    }
}
