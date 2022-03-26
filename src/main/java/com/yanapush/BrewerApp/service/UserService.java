package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User getUser(int id);
    User getUser(String username);

    User getUserByRecipe(Recipe recipe);

    boolean addUser(User user);

    boolean deleteUser(User user);

    boolean deleteUser(int id);

    boolean changeUserPassword(String username, String password);

}
