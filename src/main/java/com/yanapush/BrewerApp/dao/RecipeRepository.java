package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    public List<Recipe> findAllByAuthorUsername(String username);

    public List<Recipe> findAllByCoffeeId(int id);

    public List<Recipe> findAllByAuthorUsernameAndCoffeeId(String username, int id);
}
