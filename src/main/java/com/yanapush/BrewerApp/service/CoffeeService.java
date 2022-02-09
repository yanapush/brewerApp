package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.entity.Coffee;

import java.util.List;

public interface CoffeeService {

    Coffee getCoffee(String name);

    List<Coffee> getCoffee();

    void addCoffee(Coffee coffee);

    boolean deleteCoffee(String name);
}
