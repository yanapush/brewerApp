package com.yanapush.BrewerApp.coffee;
import org.springframework.http.ResponseEntity;

public interface CoffeeService {

    ResponseEntity<?> getCoffee(int id);

    ResponseEntity<?> getCoffee();

    void addCoffee(Coffee coffee);

    ResponseEntity<?> deleteCoffee(int id);
}
