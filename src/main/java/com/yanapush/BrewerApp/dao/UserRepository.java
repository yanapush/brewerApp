package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByRecipesContains(Recipe recipe);
}
