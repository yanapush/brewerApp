package com.yanapush.BrewerApp.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vladmihalcea.hibernate.type.json.JsonType;
import com.yanapush.BrewerApp.serializer.CustomCharacteristicSerializer;
import com.yanapush.BrewerApp.serializer.CustomCoffeeSerializer;
import com.yanapush.BrewerApp.serializer.CustomUserSerializer;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@TypeDefs(@TypeDef(name = "json", typeClass = JsonType.class))
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipe_id;
    private String recipe_name;
    private int grind_size;
    private double coffee_weight;
    private int water_volume;
    private String description;
    private String brewer;
    private int water_temperature;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<Step> steps;

    @Transient
    ObjectMapper objectMapper = new ObjectMapper();


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "coffee")
    @JsonSerialize(using = CustomCoffeeSerializer.class)
    private Coffee coffee;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "author")
    @JsonSerialize(using = CustomUserSerializer.class)
    private User author;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic", referencedColumnName = "characteristic_id")
    @JsonSerialize(using = CustomCharacteristicSerializer.class)
    private Characteristic characteristic;

    public Recipe() {
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int id) {
        this.recipe_id = id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public int getGrind_size() {
        return grind_size;
    }

    public void setGrind_size(int grind_size) {
        this.grind_size = grind_size;
    }

    public double getCoffee_weight() {
        return coffee_weight;
    }

    public void setCoffee_weight(double coffee_weight) {
        this.coffee_weight = coffee_weight;
    }

    public int getWater_volume() {
        return water_volume;
    }

    public void setWater_volume(int water_volume) {
        this.water_volume = water_volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public int getWater_temperature() {
        return water_temperature;
    }

    public void setWater_temperature(int water_temperature) {
        this.water_temperature = water_temperature;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Characteristic getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(Characteristic characteristic) {
        this.characteristic = characteristic;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> stepList) {
        this.steps = stepList;
    }

    public void addStep(Step step) {
        if (steps == null) {
            steps = new ArrayList<>();
        }
        steps.add(step);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipe_id=" + recipe_id +
                ", recipe_name='" + recipe_name + '\'' +
                '}';
    }
}