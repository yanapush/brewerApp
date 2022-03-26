package com.yanapush.BrewerApp.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.stream.Collectors;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.entity.User;
import com.yanapush.BrewerApp.security.JWTTokenHelper;
import com.yanapush.BrewerApp.security.JWTTokenProvider;
import com.yanapush.BrewerApp.security.UserInfo;
import com.yanapush.BrewerApp.service.UserServiceImpl;
import com.yanapush.BrewerApp.entity.Role;
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
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        password = passwordEncoder.encode(password);
        return authentication.isAuthenticated() ? service.changeUserPassword(authentication.getName(), password)
                ? ResponseEntity.ok(MessageConstants.SUCCESS_ADDING)
                : new ResponseEntity<>(MessageConstants.ERROR_ADDING, HttpStatus.INTERNAL_SERVER_ERROR)
                : new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user){
        User userObj=(User) userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo=new UserInfo();
        userInfo.setUsername(userObj.getUsername());
        userInfo.setRoles(userObj.getAuthorities().toArray());

        return ResponseEntity.ok(userInfo);
    }

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private UserServiceImpl service;

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println("UserResourceImpl : authenticate");
        JSONObject jsonObject = new JSONObject();
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            String username = user.getUsername();
            List<Role> role = service.getUser(username).getRoles();
            System.out.println(role.stream().map(Role::getAuthority).collect(Collectors.toList()));
            jsonObject.put("name", authentication.getName());
            jsonObject.put("authorities", authentication.getAuthorities());
            jsonObject.put("token", tokenProvider.createToken(username));
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@Valid @RequestBody User user) {
        String encodedPassword
                = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());

        Role role = new Role();
        role.setAuthority("ROLE_USER");
        role.setUser(user);
        user.addRole(role);
        return service.addUser(user) ? ResponseEntity.ok(MessageConstants.SUCCESS_ADDING) : new ResponseEntity<>(MessageConstants.ERROR_ADDING, HttpStatus.BAD_REQUEST);
    }
}
