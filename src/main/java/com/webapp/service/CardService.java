package com.webapp.service;

import com.webapp.dto.CardDto;
import com.webapp.exceptioin.NotFoundException;
import com.webapp.model.CardEntity;
import com.webapp.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public void saveCard(CardDto cardDto){

        if(!checkCard(cardDto)){
            throw new NotFoundException("Incorrect card data");
        }

        CardEntity card = new CardEntity();
        card.setNumber(cardDto.getNumber());
        card.setMonth(cardDto.getMonth());
        card.setYear(cardDto.getYear());
        card.setName(cardDto.getName());
        card.setSurname(cardDto.getSurname());
        cardRepository.save(card);
    }

    private boolean checkCard(CardDto cardDto){
        LocalDate date = LocalDate.now();
        String regex_cardNumber = "(^$|[0-9]{16})";
        String regex_cardCVC = "(^$|[0-9]{3})";

        if (!cardDto.getNumber().matches(regex_cardNumber)) {
            return false;
        }
        if (!cardDto.getCvc().toString().matches(regex_cardCVC)) {
            return false;
        }
        if(!(cardDto.getMonth()<=12 && cardDto.getMonth()>=1)){
            return false;
        }
        if(!(cardDto.getYear()>date.getYear())){
            if(cardDto.getYear()==date.getYear()){
                if(!(cardDto.getMonth()>=date.getMonthValue())){
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public List<CardEntity> getCardById(Long id){
        List<Long> ids = List.of(id);
        return cardRepository.findAllById(ids);
    }
}
