package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.characteristic.Characteristic;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @NonNull
    RecipeRepository repository;

    @Override
    public ResponseEntity<?> getRecipe(int id) {
        Optional<Recipe> recipe = repository.findById(id);
        return (recipe.equals(Optional.empty())) ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(recipe.get(), HttpStatus.OK);
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
    public void addRecipe(Recipe recipe) {
        repository.save(recipe);
    }

    @Override
    public ResponseEntity<?> addStep(int recipe_id, Step step) {
        Optional<Recipe> recipe = repository.findById(recipe_id);
        if (recipe.equals(Optional.empty())) {
            return new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
        }
        recipe.get().addStep(step);
        repository.save(recipe.get());
        return new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_STEP, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> setSteps(int recipe_id, List<Step> steps) {
        Optional<Recipe> recipe = repository.findById(recipe_id);
        if (recipe.equals(Optional.empty())) {
            new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
        }
        recipe.get().setSteps(steps);
        repository.save(recipe.get());
        return new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_STEPS, HttpStatus.OK);
    }

    @Override
    public List<Step> getSteps(int recipe_id) {
        if (repository.existsById(recipe_id)) {
            return repository.findById(recipe_id).get().getSteps();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> addCharacteristics(int recipe_id, Characteristic characteristic) {
        if (repository.existsById(recipe_id)) {
            Recipe recipe = repository.findById(recipe_id).get();
            recipe.setCharacteristic(characteristic);
            repository.save(recipe);
            return new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_CHARACTERISTICS, HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getCharacteristics(int recipe_id) {
        return (repository.existsById(recipe_id)) ?
            new ResponseEntity<>(repository.findById(recipe_id).get().getCharacteristic(), HttpStatus.OK) :
            new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> addDescription(int recipe_id, String description) {
        if (repository.existsById(recipe_id)) {
            Recipe recipe = repository.findById(recipe_id).get();
            recipe.setDescription(description);
            repository.save(recipe);
            return new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_CHARACTERISTICS, HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getDescription(int recipe_id) {
        return (repository.existsById(recipe_id)) ?
                new ResponseEntity<>(repository.findById(recipe_id).get().getDescription(), HttpStatus.OK) :
                new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<?> deleteRecipe(int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(MessageConstants.SUCCESS_DELETING_RECIPE,responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, responseHeaders, HttpStatus.NOT_FOUND);
    }
}
