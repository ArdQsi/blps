package com.webapp.controllers;

import com.webapp.auth.AuthenticationRequest;
import com.webapp.auth.RegisterRequest;
import com.webapp.dto.MessageDto;
import com.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rutube.ru")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    @PostMapping("/register")
    public MessageDto register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/authenticate")
    public MessageDto authenticate(@RequestBody AuthenticationRequest request) {
        return userService.authenticate(request);
    }
}

