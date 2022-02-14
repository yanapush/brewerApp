package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.characteristic.Characteristic;
import com.yanapush.BrewerApp.user.User;
import com.yanapush.BrewerApp.user.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
        return service.getRecipe(id);
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
        User currentUser = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUser = (User) userService.getUser(authentication.getName()).getBody();
        }
        recipe.setAuthor(currentUser);
        service.addRecipe(recipe);
    }

    @PostMapping("/step")
    public ResponseEntity<?> addStep(@RequestParam int id, @RequestBody Step step) {
        return service.addStep(id, step);
    }

    @PostMapping("{id}/steps")
    public ResponseEntity<?> addSteps(@RequestParam int id, @RequestBody List<Step> steps) {
        return service.setSteps(id, steps);
    }

    @GetMapping("/steps")
    public Object getSteps(@RequestParam int id) {
        return service.getSteps(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRecipe(@RequestParam int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!((Recipe)service.getRecipe(id).getBody()).getAuthor().getUsername().equals(authentication.getName())) {
            return new ResponseEntity<>(MessageConstants.DELETING_RECIPE_IS_FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        return  service.deleteRecipe(id);
    }

    @PostMapping("/characteristic")
    public ResponseEntity<?> addCharacteristic(@RequestParam int id, @RequestBody Characteristic characteristic) {
        characteristic.setId(id);
        return service.addCharacteristics(id, characteristic);
    }

    @GetMapping("/characteristic")
    public ResponseEntity<?> getCharacteristic(@RequestParam int id) {
        return service.getCharacteristics(id);
    }

    @PostMapping("/description")
    public ResponseEntity<?> addDescription(@RequestParam int id, @RequestBody String description) {
        return  service.addDescription(id, description);
    }

    @GetMapping("/description")
    public ResponseEntity<?> getDescription(@RequestParam int id) {
        return service.getDescription(id);
    }
}
