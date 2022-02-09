package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.Role;
import com.yanapush.BrewerApp.entity.User;

import java.util.List;

public interface UserService {
    User getUser(String username);

    User getUserByRecipe(Recipe recipe);

    void addUser(User user);

    void deleteUser(User user);

    boolean deleteUser(String username);

    boolean changeUserPassword(String username, String password);

}
