package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;

public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {
    @Autowired
    @Lazy
    RecipeRepository repository;

    @Override
    public List<Recipe> findAllByCoffeeName(String coffee) {
        List<Recipe> recipeList = repository.findAll();
        recipeList.removeIf(recipe -> {
           return !recipe.getCoffee().getCoffee_name().equals(coffee);
        });
        return recipeList;
    }

    @Override
    public List<Recipe> findAllByAuthorUsernameAndCoffeeName(String username, String coffee) {
        List<Recipe> recipeList = repository.findAllByAuthorUsername(username);
        recipeList.removeIf(recipe -> {
            return !recipe.getCoffee().getCoffee_name().equals(coffee);
        });
        return recipeList;
    }
}
