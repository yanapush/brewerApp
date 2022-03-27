package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.RecipeRepository;
import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.Step;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    @NonNull
    RecipeRepository repository;

    @Override
    public Recipe getRecipe(int id) {
        log.info("getting recipe with id=" + id);
        return repository.findById(id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
    }

    @Override
    public List<Recipe> getRecipes() {
        log.info("getting all recipes");
        return repository.findAll();
    }

    @Override
    public List<Recipe> getRecipesByUser(String username) {
        log.info("getting all recipes of " + username);
        return repository.findAllByAuthorUsername(username);
    }

    @Override
    public List<Recipe> getRecipesByCoffee(int coffee) {
        log.info("getting all recipes of coffee with id=" + coffee);
        return repository.findAllByCoffeeId(coffee);
    }

    @Override
    public List<Recipe> getRecipesByUserAndCoffee(String user, int coffee) {
        log.info("getting all recipes of " + user + " and coffee with id=" + coffee);
        return repository.findAllByAuthorUsernameAndCoffeeId(user, coffee);
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        log.info("adding recipe " + recipe.toString());
        return repository.save(recipe) == recipe;
    }

    @Override
    public boolean addStep(int recipe_id, Step step) {
        log.info("looking for recipe with id=" + recipe_id);
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        log.info("adding step " + step.toString() + " to recipe with id=" + recipe_id);
        recipe.addStep(step);
        return repository.save(recipe) == recipe;
    }

    @Override
    public boolean setSteps(int recipe_id, List<Step> steps) {
        log.info("looking for recipe with id=" + recipe_id);
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        log.info("adding steps " + steps.toString() + " to recipe with id=" + recipe_id);
        recipe.setSteps(steps);
        return repository.save(recipe) == recipe;
    }

    @Override
    public List<Step> getSteps(int recipe_id) {
        log.info("getting steps of recipe with id=" + recipe_id);
        return repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING)).getSteps();
    }

    @Override
    public boolean addCharacteristics(int recipe_id, Characteristic characteristic) {
        log.info("looking for recipe with id=" + recipe_id);
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        log.info("adding characteristics " + characteristic.toString() + " to recipe with id=" + recipe_id);
        recipe.setCharacteristic(characteristic);
        return repository.save(recipe) == recipe;
    }

    @Override
    public Characteristic getCharacteristics(int recipe_id) {
        log.info("getting characteristics of recipe with id=" + recipe_id);
        return repository.findById(recipe_id)
                .orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING))
                .getCharacteristic();
    }

    @Override
    public boolean addDescription(int recipe_id, String description) {
        log.info("looking for recipe with id=" + recipe_id);
        Recipe recipe = repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
        log.info("adding description " + description + " to recipe with id=" + recipe_id);
        recipe.setDescription(description);
        return repository.save(recipe) == recipe;
    }

    @Override
    public String getDescription(int recipe_id) {
        log.info("getting desc of recipe with id=" + recipe_id);
        return repository.findById(recipe_id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING))
                        .getDescription();
    }


    @Override
    public boolean deleteRecipe(int id) {
        log.info("looking for recipe with id=" + id);
        if (repository.existsById(id)) {
            log.info("deleting recipe with id=" + id);
            repository.deleteById(id);
            return !repository.existsById(id);
        } else {
            log.error("recipe with id=" + id + " doesn't exist");
            throw new BadCredentialsException(MessageConstants.ERROR_GETTING);
        }
    }
}
