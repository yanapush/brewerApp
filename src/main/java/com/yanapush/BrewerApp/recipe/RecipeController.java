package com.yanapush.BrewerApp.recipe;

import com.yanapush.BrewerApp.characteristic.Characteristic;
import com.yanapush.BrewerApp.user.User;
import com.yanapush.BrewerApp.user.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@CrossOrigin
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
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getRecipesByUser("yanapush");
    }

    @GetMapping("coffee")
    public List<Recipe> getRecipesOfCurrentUserByCoffee(@RequestParam int coffee) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getRecipesByUserAndCoffee("yanapush", coffee);
    }

    @GetMapping("coffee/community")
    public List<Recipe> getRecipesOfOtherUsersByCoffee(@RequestParam int coffee) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Recipe> result = service.getRecipesByCoffee(coffee);
        result.removeAll(service.getRecipesByUserAndCoffee("yanapush", coffee));
        return result;
    }

    @GetMapping("coffee/default")
    public List<Recipe> getDefaultRecipesByCoffee(@RequestParam int coffee) {
        return service.getRecipesByUserAndCoffee("yanapush", coffee);
    }

    @PostMapping
    public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipe) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

//        User currentUser = new User();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User currentUser = (User) (userService.getUser("yanapush").getBody());
//        }
        recipe.setAuthor(currentUser);
        System.out.println(recipe);
        service.addRecipe(recipe);
        return new ResponseEntity<>("it's fine", responseHeaders , HttpStatus.OK);

    }

    @PostMapping("/step")
    public ResponseEntity<?> addStep(@RequestParam int id,@Valid @RequestBody Step step) {
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
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!((Recipe)service.getRecipe(id).getBody()).getAuthor().getUsername().equals("yanapush")) {
//            return new ResponseEntity<>(MessageConstants.DELETING_RECIPE_IS_FORBIDDEN,responseHeaders, HttpStatus.FORBIDDEN);
//        }
        return  service.deleteRecipe(id);
    }

    @PostMapping("/characteristic")
    public ResponseEntity<?> addCharacteristic(@RequestParam int id, @Valid @RequestBody Characteristic characteristic) {
        characteristic.setId(id);
        return service.addCharacteristics(id, characteristic);
    }

    @GetMapping("/characteristic")
    public ResponseEntity<?> getCharacteristic(@RequestParam int id) {
        return service.getCharacteristics(id);
    }

    @PostMapping("/description")
    public ResponseEntity<?> addDescription(@RequestParam int id,@Valid @RequestBody String description) {
        return  service.addDescription(id, description);
    }

    @GetMapping("/description")
    public ResponseEntity<?> getDescription(@RequestParam int id) {
        return service.getDescription(id);
    }
}
