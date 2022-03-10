package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.user.role.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegisterController {

//    @NonNull
//    PasswordEncoder passwordEncoder;

    @NonNull
    UserServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid @RequestBody User user) {
        String encodedPassword = user.getPassword();
//                = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());

        Role role = new Role();
        role.setAuthority("ROLE_USER");
        role.setUser(user);
        user.addRole(role);
        return service.addUser(user);
    }

//    @PostMapping("/change/password")
//    public ResponseEntity<?> changePassword(
//            @RequestBody String password) {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        password = passwordEncoder.encode(password);
//        return service.changeUserPassword(authentication.getName(), password);
//    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam String username) {
        return service.getUser(username);
    }

}