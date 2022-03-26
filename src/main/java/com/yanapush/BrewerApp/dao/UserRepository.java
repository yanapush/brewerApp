package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByRecipesContains(Recipe recipe);
    Optional<User> findByUsername(String username);
}
