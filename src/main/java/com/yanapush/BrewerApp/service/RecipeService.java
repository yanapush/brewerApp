package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.Step;

import java.util.List;

public interface RecipeService {
    public Recipe getRecipe(int id);

    public List<Recipe> getRecipes();

    public List<Recipe> getRecipesByUser(String username);

    public List<Recipe> getRecipesByCoffee(String coffee);

    public List<Recipe> getRecipesByUserAndCoffee(String user, String coffee);

    public void addRecipe(Recipe recipe);

    public boolean addStep(int recipe_id, Step step);

    public boolean setSteps(int recipe_id, List<Step> steps);

    public List<Step> getSteps(int recipe_id);

    public boolean addCharacteristics(int recipe_id, Characteristic characteristic);

    public boolean addDescription(int recipe_id, String description);

    public boolean deleteRecipe(int id);
}
