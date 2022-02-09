package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.entity.*;
import com.yanapush.BrewerApp.service.RecipeServiceImpl;
import com.yanapush.BrewerApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    RecipeServiceImpl service;

    @Autowired
    UserServiceImpl userService;

    @GetMapping()
    public Object getRecipeById(@RequestParam(name = "id") int id) {
        Recipe recipe = service.getRecipe(id);
        if (recipe == null) {
            return "no such recipe";
        }

        service.addRecipe(recipe);
        return recipe;
    }

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
    public List<Recipe> getRecipesOfCurrentUserByCoffee(@RequestParam(name = "coffee") String coffee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getRecipesByUserAndCoffee(authentication.getName(), coffee);
    }

    @GetMapping("coffee/community")
    public List<Recipe> getRecipesOfOtherUsersByCoffee(@RequestParam(name = "coffee") String coffee) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Recipe> result = service.getRecipesByCoffee(coffee);
        result.removeAll(service.getRecipesByUserAndCoffee(authentication.getName(), coffee));
        return result;
    }

    @GetMapping("coffee/default")
    public List<Recipe> getDefaultRecipesByCoffee(@RequestParam(name = "coffee") String coffee) {
        return service.getRecipesByUserAndCoffee("yanapush", coffee);
    }

    @PostMapping
    public void addRecipe(@RequestBody Recipe recipe) {
        recipe.getCoffee().addRecipe(recipe);
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
    public String addStep(@RequestParam(name = "id") int id, @RequestBody Step step) {
        if (service.addStep(id, step)) {
            return "Step was successfully added";
        }
        return "No such recipe";
    }

    @PostMapping("{id}/steps")
    public String addSteps(@RequestParam(name = "id") int id, @RequestBody List<Step> steps) {
        if (service.setSteps(id, steps)) {
            return "Steps were successfully added";
        }
        return "No such recipe";
    }

    @GetMapping("/steps")
    public Object getSteps(@RequestParam(name = "id") int id) {
        return service.getSteps(id);
    }

    @DeleteMapping
    public String deleteRecipe(@RequestParam(name = "id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (service.getRecipe(id).getAuthor().getUsername() != authentication.getName()) {
            return "deleting is forbidden";
        }
        if (service.deleteRecipe(id)) {
            return "recipe was successfully deleted";
        }
        return "no such recipe";
    }

    @PostMapping("/characteristic")
    public String addCharacteristic(@RequestParam(name = "id") int id, @RequestBody Characteristic characteristic) {
        characteristic.setCharacteristic_id(id);
        if (service.addCharacteristics(id, characteristic)) {
            return "characteristics are successfully added";
        }
        return "no recipe with such id";
    }

    @GetMapping("/characteristic")
    public Object getCharacteristic(@RequestParam(name = "id") int id) {
        Recipe recipe = service.getRecipe(id);
        if (recipe == null) {
            return "no such recipe!";
        }
        return recipe.getCharacteristic();
    }

    @PostMapping("/description")
    public String addDescription(@RequestParam(name = "id") int id, @RequestBody String description) {
        if (service.addDescription(id, description)) {
            return "description are successfully added";
        }
        return "no recipe with such id";
    }

    @GetMapping("/description")
    public String getDescription(@RequestParam(name = "id") int id) {
        Recipe recipe = service.getRecipe(id);
        if (recipe == null) {
            return "no such recipe!";
        }
        return recipe.getDescription();
    }

}
