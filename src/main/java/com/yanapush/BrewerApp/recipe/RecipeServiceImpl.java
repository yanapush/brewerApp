package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.characteristic.Characteristic;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @NonNull
    RecipeRepository repository;

    @Override
    public Recipe getRecipe(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Recipe> getRecipes() {
        return repository.findAll();
    }

    @Override
    public List<Recipe> getRecipesByUser(String username) {
        return repository.findAllByAuthorUsername(username);
    }

    @Override
    public List<Recipe> getRecipesByCoffee(String coffee) {
        return repository.findAllByCoffeeName(coffee);
    }

    @Override
    public List<Recipe> getRecipesByUserAndCoffee(String user, String coffee) {
        return repository.findAllByAuthorUsernameAndCoffeeName(user, coffee);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        repository.save(recipe);
    }

    @Override
    public boolean addStep(int recipe_id, Step step) {
        Recipe recipe = repository.findById(recipe_id).orElse(null);
        if (recipe != null) {
            recipe.addStep(step);
            repository.save(recipe);
            return true;
        }
        return false;
    }

    @Override
    public boolean setSteps(int recipe_id, List<Step> steps) {
        Recipe recipe = repository.findById(recipe_id).orElse(null);
        if (recipe != null) {
            recipe.setSteps(steps);
            repository.save(recipe);
            return true;
        }
        return false;
    }

    @Override
    public List<Step> getSteps(int recipe_id) {
        if (repository.existsById(recipe_id)) {
            return repository.findById(recipe_id).get().getSteps();
        }
        return null;
    }

    @Override
    public boolean addCharacteristics(int recipe_id, Characteristic characteristic) {
        if (repository.existsById(recipe_id)) {
            Recipe recipe = repository.findById(recipe_id).get();
            recipe.setCharacteristic(characteristic);
            repository.save(recipe);
            return true;
        }
        return false;
    }

    @Override
    public boolean addDescription(int recipe_id, String description) {
        if (repository.existsById(recipe_id)) {
            Recipe recipe = repository.findById(recipe_id).get();
            recipe.setDescription(description);
            repository.save(recipe);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
