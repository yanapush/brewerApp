package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.recipe.Recipe;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User getUser(int id);
    User getUser(String username);

    User getUserByRecipe(Recipe recipe);

    ResponseEntity<?> addUser(User user);

    void deleteUser(User user);

    boolean deleteUser(int id);

    boolean changeUserPassword(String username, String password);

}
