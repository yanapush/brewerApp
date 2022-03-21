package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.recipe.Recipe;
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
