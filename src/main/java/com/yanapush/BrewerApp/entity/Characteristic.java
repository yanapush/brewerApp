package com.yanapush.BrewerApp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yanapush.BrewerApp.serializer.CustomCharacteristicSerializer;

import javax.persistence.*;

@Entity
@Table
@JsonSerialize(using = CustomCharacteristicSerializer.class)
public class Characteristic {

    @Id
    private int characteristic_id;
    private int acidity;
    private int sweetness;
    private int density;
    private int bitterness;

    @OneToOne(mappedBy = "characteristic", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private Recipe recipe;

    public Characteristic() {
    }

    public int getCharacteristic_id() {
        return characteristic_id;
    }

    public void setCharacteristic_id(int characteristic_id) {
        this.characteristic_id = characteristic_id;
    }

    public int getAcidity() {
        return acidity;
    }

    public void setAcidity(int acidity) {
        this.acidity = acidity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getSweetness() {
        return sweetness;
    }

    public void setSweetness(int sweetness) {
        this.sweetness = sweetness;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public int getBitterness() {
        return bitterness;
    }

    public void setBitterness(int bitterness) {
        this.bitterness = bitterness;
    }

    @Override
    public String toString() {
        return "characteristic with id " + characteristic_id;
    }
}
