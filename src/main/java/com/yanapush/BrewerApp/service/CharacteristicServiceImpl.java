package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.dao.CharacteristicRepository;
import com.yanapush.BrewerApp.entity.Characteristic;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CharacteristicServiceImpl implements CharacteristicService {

    @NonNull
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
