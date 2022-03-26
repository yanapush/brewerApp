package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.RecipeRepository;
import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.Step;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @NonNull
    RecipeRepository repository;

    @Override
    public Recipe getRecipe(int id) {
        return repository.findById(id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
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
    public List<Recipe> getRecipesByCoffee(int coffee) {
        return repository.findAllByCoffeeId(coffee);
    }

    @Override
    public List<Recipe> getRecipesByUserAndCoffee(String user, int coffee) {
        return repository.findAllByAuthorUsernameAndCoffeeId(user, coffee);
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        return repository.save(recipe) == recipe;
    }

    @Override
    public boolean addStep(int recipe_id, Step step) {
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        recipe.addStep(step);
        return repository.save(recipe) == recipe;
    }

    @Override
    public boolean setSteps(int recipe_id, List<Step> steps) {
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        recipe.setSteps(steps);
        return repository.save(recipe) == recipe;
    }

    @Override
    public List<Step> getSteps(int recipe_id) {
        return repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING)).getSteps();
    }

    @Override
    public boolean addCharacteristics(int recipe_id, Characteristic characteristic) {
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        recipe.setCharacteristic(characteristic);
        return repository.save(recipe) == recipe;
    }

    @Override
    public Characteristic getCharacteristics(int recipe_id) {
        return repository.findById(recipe_id)
                .orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING))
                .getCharacteristic();
    }

    @Override
    public boolean addDescription(int recipe_id, String description) {
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        recipe.setDescription(description);
        return repository.save(recipe) == recipe;
    }

    @Override
    public String getDescription(int recipe_id) {
                return repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING))
                        .getDescription();
    }


    @Override
    public boolean deleteRecipe(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return !repository.existsById(id);
        } else {
            throw new BadCredentialsException(MessageConstants.ERROR_GETTING);
        }
    }
}
