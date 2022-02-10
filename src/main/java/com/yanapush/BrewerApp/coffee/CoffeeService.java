package com.yanapush.BrewerApp.coffee;

import com.yanapush.BrewerApp.coffee.Coffee;

import java.util.List;

public interface CoffeeService {

    Coffee getCoffee(int id);

    List<Coffee> getCoffee();

    void addCoffee(Coffee coffee);

    boolean deleteCoffee(int id);
}
