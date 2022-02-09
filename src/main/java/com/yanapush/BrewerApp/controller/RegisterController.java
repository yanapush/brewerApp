package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.entity.Role;
import com.yanapush.BrewerApp.entity.User;
import com.yanapush.BrewerApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl service;

    @PostMapping("/register")
    public String doRegister(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());

        Role role = new Role();
        role.setAuthority("USER");
        role.setUser(user);
        user.addRole(role);
        service.addUser(user);

        return "register-success";
    }

    @PostMapping("/change/password")
    public String changePassword(
            @RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String encodedPassword = passwordEncoder.encode(password);
        if (service.changeUserPassword(authentication.getName(), password)) {
            return "password changed successfully";
        }
        return "password wasn't changed";
    }

    @GetMapping("/user")
    public Object getUser(@RequestParam(name = "name") String username) {
        User user = service.getUser(username);
        if (user == null) {
            return "no such user";
        }
        return user;
    }

}