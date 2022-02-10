package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.recipe.Recipe;

import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> findAllByCoffee(int coffee);

    public List<Recipe> findAllByAuthorUsernameAndCoffeeId(String username, int coffee);
}
