package com.yanapush.BrewerApp.coffee;

import com.yanapush.BrewerApp.coffee.Coffee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CoffeeService {

    ResponseEntity<?> getCoffee(int id);

    ResponseEntity<?> getCoffee();

    void addCoffee(Coffee coffee);

    ResponseEntity<?> deleteCoffee(int id);
}
