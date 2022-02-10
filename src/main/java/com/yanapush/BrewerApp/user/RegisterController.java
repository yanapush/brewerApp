package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.user.role.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    @NonNull
    PasswordEncoder passwordEncoder;

    @NonNull
    UserServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
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
    public ResponseEntity<String> changePassword(
            @RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String encodedPassword = passwordEncoder.encode(password);
        return (service.changeUserPassword(authentication.getName(), password))
                ? new ResponseEntity<>(MessageConstants.SUCCESS_PASSWORD_CHANGE, HttpStatus.OK)
        : new ResponseEntity<>(MessageConstants.ERROR_CHANGING_PASSWORD, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam int id) {
        User user = service.getUser(id);
        return (user == null) ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_USER, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(user, HttpStatus.OK);
    }

}