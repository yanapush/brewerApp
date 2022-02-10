package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.characteristic.Characteristic;
import com.yanapush.BrewerApp.coffee.Coffee;
import com.yanapush.BrewerApp.user.User;
import com.yanapush.BrewerApp.user.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor

public class RecipeController {

    @NonNull
    RecipeServiceImpl service;

    @NonNull
    UserServiceImpl userService;

    @GetMapping()
    public ResponseEntity<?> getRecipeById(@RequestParam int id) {
        Recipe recipe = service.getRecipe(id);
        if (recipe == null) {
            return new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
        }
        service.addRecipe(recipe);
        return  new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    // todo do i always need to return responseEntity?
    @GetMapping("/all")
    public List<Recipe> getRecipes() {
        return service.getRecipes();
    }

    @GetMapping("user")
    public List<Recipe> getRecipesOfCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getRecipesByUser(authentication.getName());
    }

    @GetMapping("coffee")
    public List<Recipe> getRecipesOfCurrentUserByCoffee(@RequestParam int coffee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getRecipesByUserAndCoffee(authentication.getName(), coffee);
    }

    @GetMapping("coffee/community")
    public List<Recipe> getRecipesOfOtherUsersByCoffee(@RequestParam int coffee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Recipe> result = service.getRecipesByCoffee(coffee);
        result.removeAll(service.getRecipesByUserAndCoffee(authentication.getName(), coffee));
        return result;
    }

    @GetMapping("coffee/default")
    public List<Recipe> getDefaultRecipesByCoffee(@RequestParam int coffee) {
        return service.getRecipesByUserAndCoffee("yanapush", coffee);
    }

    @PostMapping
    public void addRecipe(@RequestBody Recipe recipe) {
        Coffee coffee = recipe.getCoffee();
        coffee.addRecipe(recipe);
        User currentUser = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUser = userService.getUser(authentication.getName());
        }
        recipe.setAuthor(currentUser);
        currentUser.addRecipe(recipe);
        service.addRecipe(recipe);
    }

    @PostMapping("/step")
    public ResponseEntity<String> addStep(@RequestParam int id, @RequestBody Step step) {
        return (service.addStep(id, step))
                ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_STEP, HttpStatus.OK)
                : new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @PostMapping("{id}/steps")
    public ResponseEntity<String> addSteps(@RequestParam int id, @RequestBody List<Step> steps) {
        return (service.setSteps(id, steps))
                ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_STEPS, HttpStatus.OK)
                : new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/steps")
    public Object getSteps(@RequestParam int id) {
        return service.getSteps(id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRecipe(@RequestParam int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!service.getRecipe(id).getAuthor().getUsername().equals(authentication.getName())) {
            return new ResponseEntity<>(MessageConstants.DELETING_RECIPE_IS_FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        return  (service.deleteRecipe(id))
                ? new ResponseEntity<>(MessageConstants.SUCCESS_DELETING_RECIPE, HttpStatus.OK)
                : new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/characteristic")
    public ResponseEntity<String> addCharacteristic(@RequestParam int id, @RequestBody Characteristic characteristic) {
        characteristic.setId(id);
            return  (service.addCharacteristics(id, characteristic))
                    ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_CHARACTERISTICS, HttpStatus.OK)
                    : new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/characteristic")
    public ResponseEntity<?> getCharacteristic(@RequestParam int id) {
        Recipe recipe = service.getRecipe(id);
        return  (recipe == null)
                ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(recipe.getCharacteristic(), HttpStatus.OK);
    }

    @PostMapping("/description")
    public ResponseEntity<String> addDescription(@RequestParam int id, @RequestBody String description) {
        return  (service.addDescription(id, description))
                ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING_DESCRIPTION, HttpStatus.OK)
                : new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/description")
    public ResponseEntity<?> getDescription(@RequestParam int id) {
        Recipe recipe = service.getRecipe(id);
        return  (recipe == null)
                ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_RECIPE, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(recipe.getDescription(), HttpStatus.OK);
    }
}
