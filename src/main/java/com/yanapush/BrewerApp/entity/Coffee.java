package com.yanapush.BrewerApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table
public class Coffee {
    @Id
    private String coffee_name;
    private String country;
    private String process;
    private String description;

    @OneToMany(mappedBy = "coffee", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Recipe> recipes;

    public Coffee() {
    }

    public String getCoffee_name() {
        return coffee_name;
    }

    public void setCoffee_name(String coffee_name) {
        this.coffee_name = coffee_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void addRecipe(Recipe recipe) {
        if (recipes == null) {
            recipes = new LinkedList<>();
        }
        recipes.add(recipe);
    }

    @Override
    public String toString() {
        return "Coffee{" + "coffee_name='" + coffee_name + '\'' + '}';
    }
}
