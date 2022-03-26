package com.yanapush.BrewerApp.service;
import com.yanapush.BrewerApp.entity.Coffee;
import org.springframework.http.ResponseEntity;

public interface CoffeeService {

    ResponseEntity<?> getCoffee(int id);

    ResponseEntity<?> getCoffee();

    void addCoffee(Coffee coffee);

    ResponseEntity<?> deleteCoffee(int id);
}
