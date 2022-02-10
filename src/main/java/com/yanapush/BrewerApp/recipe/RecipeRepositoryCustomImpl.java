package com.yanapush.BrewerApp.recipe;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@RequiredArgsConstructor
public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {

    @Lazy
    RecipeRepository repository;

    @Override
    public List<Recipe> findAllByCoffeeName(String coffee) {
        List<Recipe> recipeList = repository.findAll();
        recipeList.removeIf(recipe -> {
           return !recipe.getCoffee_id().getCoffee_name().equals(coffee);
        });
        return recipeList;
    }

    @Override
    public List<Recipe> findAllByAuthorUsernameAndCoffeeName(String username, String coffee) {
        List<Recipe> recipeList = repository.findAllByAuthorUsername(username);
        recipeList.removeIf(recipe -> {
            return !recipe.getCoffee_id().getCoffee_name().equals(coffee);
        });
        return recipeList;
    }
}
