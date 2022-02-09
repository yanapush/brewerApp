package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>, RecipeRepositoryCustom {
    public List<Recipe> findAllByAuthorUsername(String username);
}
