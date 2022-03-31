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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class RecipeController {

    @NonNull
    RecipeServiceImpl service;

    @Autowired
    private MessageConstants constants;

    @GetMapping()
    public ResponseEntity<?> getRecipeById(@RequestParam int id) {
        log.info("got request to get recipe with id=" + id);
        return ResponseEntity.ok(service.getRecipe(id));
    }

    @GetMapping("/all")
    public List<Recipe> getRecipes() {
        log.info("got request to get all recipes");
        return service.getRecipes();
    }

    @GetMapping("user")
    public List<Recipe> getRecipesOfCurrentUser() {
        log.info("got request to get all recipes of current user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getRecipesByUser(authentication.getName());
    }

    @GetMapping("coffee")
    public List<Recipe> getRecipesOfCurrentUserByCoffee(@RequestParam int coffee) {
        log.info("got request to get all recipes of current user with coffee id=" + coffee);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return service.getRecipesByUserAndCoffee(authentication.getName(), coffee);
    }

    @GetMapping("coffee/community")
    public List<Recipe> getRecipesOfOtherUsersByCoffee(@RequestParam int coffee) {
        log.info("got request to get all recipes of other user with coffee id=" + coffee);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Recipe> result = service.getRecipesByCoffee(coffee);
        result.removeAll(service.getRecipesByUserAndCoffee(authentication.getName(), coffee));
        return result;
    }

    @GetMapping("coffee/default")
    public List<Recipe> getDefaultRecipesByCoffee(@RequestParam int coffee) {
        log.info("got request to get all default recipes with coffee id=" + coffee);
        return service.getRecipesByUserAndCoffee("yanapush", coffee);
    }

    @PostMapping
    public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipe) {
        log.info("got request to add recipe " + recipe.toString());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("recipe", service.addRecipe(recipe));
        body.put("message", String.format(constants.SUCCESS_ADDING, "recipe"));
        return new ResponseEntity<>(body, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/step")
    public ResponseEntity<?> addStep(@RequestParam int id, @Valid @RequestBody Step step) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("recipe", service.addStep(id, step));
        body.put("message", String.format(constants.SUCCESS_ADDING_TO, "step", "recipe"));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("{id}/steps")
    public ResponseEntity<?> addSteps(@RequestParam int id, @RequestBody List<Step> steps) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("recipe",  service.setSteps(id, steps));
        body.put("message", String.format(constants.SUCCESS_ADDING_TO, "steps", "recipe"));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/steps")
    public ResponseEntity<?> getSteps(@RequestParam int id) {
        return ResponseEntity.ok(service.getSteps(id));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRecipe(@RequestParam int id) {
        log.info("got request to delete recipe with id=" + id);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        responseHeaders.set("Access-Control-Allow-Credentials", "true");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(service.getRecipe(id).getAuthor().equals(authentication.getName()))) {
            log.error("recipe with id=" + id + " doesn't belong to current user");
            return new ResponseEntity<>(String.format(constants.IS_FORBIDDEN, "recipe"), responseHeaders, HttpStatus.FORBIDDEN);
        }
        log.info("recipe with id=" + id + " belongs to current user");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("isSuccess",   service.deleteRecipe(id));
        body.put("message", String.format(constants.SUCCESS_DELETING_BY_ID, "recipe", id));
        return new ResponseEntity<>(body, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/characteristic")
    public ResponseEntity<?> addCharacteristic(@RequestParam int id, @Valid @RequestBody Characteristic characteristic) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("recipe", service.addCharacteristics(id, characteristic));
        body.put("message", String.format(constants.SUCCESS_ADDING_TO, "characteristics", "recipe"));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/characteristic")
    public ResponseEntity<?> getCharacteristic(@RequestParam int id) {
        return ResponseEntity.ok(service.getCharacteristics(id));
    }

    @PostMapping("/description")
    public ResponseEntity<?> addDescription(@RequestParam int id, @Valid @RequestBody String description) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("recipe", service.addDescription(id, description));
        body.put("message", String.format(constants.SUCCESS_ADDING_TO, "description", "recipe"));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/description")
    public ResponseEntity<?> getDescription(@RequestParam int id) {
        return ResponseEntity.ok(service.getDescription(id));
    }
}
