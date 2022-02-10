//package com.yanapush.BrewerApp.recipe;
//
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {
//
//    @NonNull
//    RecipeRepository repository;
//
//    @Override
//    public List<Recipe> findAllByCoffee(int coffee) {
//        List<Recipe> recipeList = repository.findAll();
//        recipeList.removeIf(recipe -> {
//           return !(recipe.getCoffee_id().getCoffee_id() == coffee);
//        });
//        return recipeList;
//    }
//
//    @Override
//    public List<Recipe> findAllByAuthorUsernameAndCoffeeId(String username, int coffee) {
//        List<Recipe> recipeList = repository.findAllByAuthorUsername(username);
//        recipeList.removeIf(recipe -> {
//            return !(recipe.getCoffee_id().getCoffee_id() == coffee);
//        });
//        return recipeList;
//    }
//}
