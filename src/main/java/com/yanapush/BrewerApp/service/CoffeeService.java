package com.yanapush.BrewerApp.service;
import com.yanapush.BrewerApp.entity.Coffee;

import java.util.List;

public interface CoffeeService {

    Coffee getCoffee(int id) throws Exception;

    List<Coffee> getCoffee();

    Coffee addCoffee(Coffee coffee);

    boolean deleteCoffee(int id);
}
