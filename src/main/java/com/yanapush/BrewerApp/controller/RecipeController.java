package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Recipe;
import com.yanapush.BrewerApp.entity.Step;
import com.yanapush.BrewerApp.entity.User;
import com.yanapush.BrewerApp.service.RecipeServiceImpl;
import com.yanapush.BrewerApp.service.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return ResponseEntity.ok(service.getRecipe(id));
    }

    @GetMapping("/all")
    public List<Recipe> getRecipes() {
        return service.getRecipes();
    }

    @GetMapping("user")
    public List<Recipe> getRecipesOfCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
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
    public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipe) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        User currentUser = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUser = (userService.getUser(authentication.getName()));
        }
        recipe.setAuthor(currentUser);

        return  service.addRecipe(recipe)
                ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING, responseHeaders, HttpStatus.OK) :
                new ResponseEntity<>(MessageConstants.ERROR_ADDING, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/step")
    public ResponseEntity<?> addStep(@RequestParam int id, @Valid @RequestBody Step step) {
        return service.addStep(id, step) ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING, HttpStatus.OK) :
                new ResponseEntity<>(MessageConstants.ERROR_ADDING, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("{id}/steps")
    public ResponseEntity<?> addSteps(@RequestParam int id, @RequestBody List<Step> steps) {
        return service.setSteps(id, steps) ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING, HttpStatus.OK) :
                new ResponseEntity<>(MessageConstants.ERROR_ADDING, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/steps")
    public ResponseEntity<?> getSteps(@RequestParam int id) {
        return ResponseEntity.ok(service.getSteps(id));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRecipe(@RequestParam int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(service.getRecipe(id).getAuthor().equals("yanapush"))) {
            return new ResponseEntity<>(MessageConstants.IS_FORBIDDEN, responseHeaders, HttpStatus.FORBIDDEN);
        }
        return service.deleteRecipe(id) ? new ResponseEntity<>(MessageConstants.SUCCESS_DELETIG, responseHeaders, HttpStatus.OK) :
                new ResponseEntity<>(MessageConstants.ERROR_DELETING, responseHeaders, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/characteristic")
    public ResponseEntity<?> addCharacteristic(@RequestParam int id, @Valid @RequestBody Characteristic characteristic) {
        characteristic.setId(id);
        return service.addCharacteristics(id, characteristic) ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING, HttpStatus.OK) :
                new ResponseEntity<>(MessageConstants.ERROR_ADDING, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/characteristic")
    public ResponseEntity<?> getCharacteristic(@RequestParam int id) {
        return ResponseEntity.ok(service.getCharacteristics(id));
    }

    @PostMapping("/description")
    public ResponseEntity<?> addDescription(@RequestParam int id, @Valid @RequestBody String description) {
        return service.addDescription(id, description) ? new ResponseEntity<>(MessageConstants.SUCCESS_ADDING, HttpStatus.OK) :
                new ResponseEntity<>(MessageConstants.ERROR_ADDING, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/description")
    public ResponseEntity<?> getDescription(@RequestParam int id) {
        return ResponseEntity.ok(service.getDescription(id));
    }
}