package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.dao.CharacteristicRepository;
import com.yanapush.BrewerApp.dao.CoffeeRepository;
import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Coffee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class CharacteristicServiceImpl implements CharacteristicService {

    @Autowired
    CharacteristicRepository repository;

    @Override
    public Characteristic getCharacteristic(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Characteristic getCharacteristicByRecipeId(int id) {
        return repository.findById(id).orElse(null);
    }
}
