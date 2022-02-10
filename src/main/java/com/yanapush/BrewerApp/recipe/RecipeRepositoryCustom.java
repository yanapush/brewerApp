package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.recipe.Recipe;

import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> findAllByCoffeeName(String coffee);

    public List<Recipe> findAllByAuthorUsernameAndCoffeeName(String username, String coffee);
}
