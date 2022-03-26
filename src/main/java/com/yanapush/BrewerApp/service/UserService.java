package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getUser(int id);
    User getUser(String username);

    ResponseEntity<?> getUserByRecipe(Recipe recipe);

    ResponseEntity<?> addUser(User user);

    void deleteUser(User user);

    boolean deleteUser(int id);

    ResponseEntity<?> changeUserPassword(String username, String password);

}
