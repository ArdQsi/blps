package com.webapp.controllers;

import com.webapp.dto.AddModeratorRequestDto;
import com.webapp.dto.GenreDto;
import com.webapp.dto.MessageDto;
import com.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rutube.ru/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/add")
    public MessageDto addModerator(@RequestBody AddModeratorRequestDto addModeratorRequestDto) {
        return userService.addModerator(addModeratorRequestDto.getId());
    }

    @PostMapping("/remove")
    public MessageDto removeModerator(@RequestBody AddModeratorRequestDto addModeratorRequestDto) {
        return userService.removeModerator(addModeratorRequestDto.getId());
    }
}
