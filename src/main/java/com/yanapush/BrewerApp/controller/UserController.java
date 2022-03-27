package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.service.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserController {
    @NonNull
    UserServiceImpl service;

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam String username) {
        log.info("got request to get user " + username);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() ? new ResponseEntity<>(service.getUser(username), HttpStatus.OK): new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

}