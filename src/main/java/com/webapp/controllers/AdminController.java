package com.webapp.controllers;

import com.webapp.dto.MessageDto;
import com.webapp.dto.RequestVideoDto;
import com.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rutube.ru/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/moderators")
    public MessageDto addModerator(@RequestBody RequestVideoDto requestVideoDto) {
        return userService.addModerator(requestVideoDto.getUserId());
    }

    @DeleteMapping("/moderators/{id}")
    public MessageDto removeModerator(@PathVariable Long id) {
        return userService.removeModerator(id);
    }
}
