package com.yanapush.BrewerApp.characteristic;

import com.yanapush.BrewerApp.characteristic.Characteristic;

public interface CharacteristicService {

    Characteristic getCharacteristic(int id);

    Characteristic getCharacteristicByRecipeId(int id);
}
