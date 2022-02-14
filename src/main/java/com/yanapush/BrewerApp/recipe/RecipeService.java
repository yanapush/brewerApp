package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.characteristic.Characteristic;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecipeService {
    public ResponseEntity<?> getRecipe(int id);

    public List<Recipe> getRecipes();

    public List<Recipe> getRecipesByUser(String username);

    public List<Recipe> getRecipesByCoffee(int coffee);

    public List<Recipe> getRecipesByUserAndCoffee(String user, int coffee);

    public void addRecipe(Recipe recipe);

    public ResponseEntity<?> addStep(int recipe_id, Step step);

    public ResponseEntity<?> setSteps(int recipe_id, List<Step> steps);

    public List<Step> getSteps(int recipe_id);

    public ResponseEntity<?> addCharacteristics(int recipe_id, Characteristic characteristic);

    public ResponseEntity<?> getCharacteristics(int recipe_id);

    public ResponseEntity<?> addDescription(int recipe_id, String description);

    public ResponseEntity<?> getDescription(int recipe_id);

    public ResponseEntity<?> deleteRecipe(int id);
}
