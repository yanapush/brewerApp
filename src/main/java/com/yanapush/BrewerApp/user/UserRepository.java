package com.yanapush.BrewerApp.user;

import com.yanapush.BrewerApp.recipe.Recipe;
import com.yanapush.BrewerApp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByRecipesContains(Recipe recipe);
    Optional<User> findByUsername(String username);
}
