package com.yanapush.BrewerApp.security;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.stream.Collectors;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.yanapush.BrewerApp.user.User;
import com.yanapush.BrewerApp.user.UserServiceImpl;
import com.yanapush.BrewerApp.user.role.Role;
import lombok.NonNull;
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
        return authentication.isAuthenticated() ? service.changeUserPassword(authentication.getName(), password) : new ResponseEntity<>("not authorized", HttpStatus.FORBIDDEN);
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
            List<String> r = role.stream().map(Role::getAuthority).collect(Collectors.toList());
            System.out.println(role.stream().map(Role::getAuthority).collect(Collectors.toList()));
            jsonObject.put("name", authentication.getName());
            jsonObject.put("authorities", authentication.getAuthorities());
            jsonObject.put("token", tokenProvider.createToken(username));
            return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
        }
        return null;
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
        return service.addUser(user);
    }
}
