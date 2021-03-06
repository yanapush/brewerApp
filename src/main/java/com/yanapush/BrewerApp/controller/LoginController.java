package com.yanapush.BrewerApp.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.entity.User;
import com.yanapush.BrewerApp.security.JWTTokenProvider;
import com.yanapush.BrewerApp.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class LoginController {

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MessageConstants constants;

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("got request to change password to " + password);
        if (authentication.isAuthenticated()) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("user", service.changeUserPassword(authentication.getName(), password));
            body.put("message", String.format(constants.SUCCESS_ADDING, "password"));
            return new ResponseEntity<>(body, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        JSONObject jsonObject = new JSONObject();
        log.info("got request to authenticate user");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            log.info("user " + user.getUsername() + " is authenticated");
            String username = user.getUsername();
            Object principal = authentication.getPrincipal();
            jsonObject.put("name", ((UserDetails)principal).getUsername());
            jsonObject.put("authorities", authentication.getAuthorities());
            jsonObject.put("token", tokenProvider.createToken(username));
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        log.error("user " + user.getUsername() + " is not authorized");
        return new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid @RequestBody User user) {
        log.info("got request to register user " + user.toString());
        service.addUser(user);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("user", service.addUser(user));
        body.put("message", String.format(constants.SUCCESS_ADDING, "user"));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
