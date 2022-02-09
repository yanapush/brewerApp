package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Coffee;

import java.util.List;

public interface CharacteristicService {

    Characteristic getCharacteristic(int id);

    Characteristic getCharacteristicByRecipeId(int id);
}
