package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Recipe;

import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> findAllByCoffeeName(String coffee);

    public List<Recipe> findAllByAuthorUsernameAndCoffeeName(String username, String coffee);
}
