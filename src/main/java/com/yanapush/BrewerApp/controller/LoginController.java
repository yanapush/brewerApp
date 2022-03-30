package com.yanapush.BrewerApp.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.entity.User;
import com.yanapush.BrewerApp.security.JWTTokenProvider;
import com.yanapush.BrewerApp.security.UserInfo;
import com.yanapush.BrewerApp.service.UserServiceImpl;
import com.yanapush.BrewerApp.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    MessageConstants constants = new MessageConstants();

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        password = passwordEncoder.encode(password);
        log.info("got request to change password to " + password);
        if (authentication.isAuthenticated()) {
            service.changeUserPassword(authentication.getName(), password);
            return ResponseEntity.ok(String.format(constants.SUCCESS_ADDING, "password"));
        } else {
            return new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user) {
        User userObj = (User) userDetailsService.loadUserByUsername(user.getName());
        log.info("got request to get userinfo " + user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userObj.getUsername());
        userInfo.setRoles(userObj.getAuthorities().toArray());
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        JSONObject jsonObject = new JSONObject();
        log.info("got request to authenticate user");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            log.info("user " + user.getUsername() + " is authenticated");
            String username = user.getUsername();
            jsonObject.put("name", authentication.getName());
            jsonObject.put("authorities", authentication.getAuthorities());
            jsonObject.put("token", tokenProvider.createToken(username));
            log.info("returning " + jsonObject);
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        }
        log.error("user " + user.getUsername() + " is not authorized");
        return new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid @RequestBody User user) {
        log.info("got request to register user " + user.toString());
        String encodedPassword
                = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());
        Role role = new Role();
        role.setAuthority("ROLE_USER");
        role.setUser(user);
        user.addRole(role);
        service.addUser(user);
        return ResponseEntity.ok(String.format(constants.SUCCESS_ADDING, "user"));
    }
}
