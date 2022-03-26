package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.entity.Characteristic;

public interface CharacteristicService {

    Characteristic getCharacteristic(int id);

    Characteristic getCharacteristicByRecipeId(int id);
}
