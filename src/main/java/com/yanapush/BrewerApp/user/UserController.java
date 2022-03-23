package com.yanapush.BrewerApp.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    @NonNull
    UserServiceImpl service;

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() ? new ResponseEntity<>(service.getUser(username), HttpStatus.OK): new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

}