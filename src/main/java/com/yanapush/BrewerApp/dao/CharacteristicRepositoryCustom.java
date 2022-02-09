package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface CharacteristicRepositoryCustom {

    public Optional<Characteristic> findByRecipeId(int recipeId);

    public Optional<Characteristic> findById(int id);
}
