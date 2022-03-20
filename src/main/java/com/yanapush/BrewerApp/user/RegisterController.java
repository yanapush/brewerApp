package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.user.role.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RegisterController {

    @NonNull
    PasswordEncoder passwordEncoder;

    @NonNull
    UserServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid @RequestBody User user) {
        String encodedPassword
//                = user.getPassword();
                = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());

        Role role = new Role();
        role.setAuthority("ROLE_USER");
        role.setUser(user);
        user.addRole(role);
        return service.addUser(user);
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        password = passwordEncoder.encode(password);
        return authentication.isAuthenticated() ? service.changeUserPassword(authentication.getName(), password) : new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("/////////////////////////");
        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getName());
        System.out.println(authentication.getAuthorities());
        System.out.println(username);
        return authentication.isAuthenticated() ? service.getUser(username): new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

}