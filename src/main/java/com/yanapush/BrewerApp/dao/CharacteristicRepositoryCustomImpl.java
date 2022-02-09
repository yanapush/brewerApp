package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Characteristic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Optional;

public class CharacteristicRepositoryCustomImpl implements CharacteristicRepositoryCustom {

    @Autowired
    @Lazy
    CharacteristicRepository repository;

    @Override
    public Optional<Characteristic> findByRecipeId(int recipeId) {
        List<Characteristic> characteristics = repository.findAll();
        for (Characteristic characteristic : characteristics) {
            if (characteristic.getRecipe().getRecipe_id() == recipeId) {
                return Optional.of(characteristic);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Characteristic> findById(int id) {
        return repository.findById(id);
    }
}
