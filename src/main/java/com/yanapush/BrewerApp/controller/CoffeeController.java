package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.entity.Coffee;
import com.yanapush.BrewerApp.service.CoffeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    CoffeeServiceImpl coffeeServiceImpl;

    @GetMapping
    public Object getCoffee(@RequestParam(name = "name", required = false) String name) {
        if (name == null) {
            return coffeeServiceImpl.getCoffee();
        } else {
            Coffee coffee = coffeeServiceImpl.getCoffee(name);
            if (coffee == null) {
                return "no such coffee!";
            }
            return coffee;
        }
    }

    @PostMapping
    public void addCoffee(@RequestBody Coffee coffee) {
        coffeeServiceImpl.addCoffee(coffee);
    }

    @DeleteMapping
    public String deleteCoffee(@RequestParam(name = "name") String name) {
        if (coffeeServiceImpl.deleteCoffee(name)) {
            return "coffee was successfully deleted";
        }
        return "no such coffee";
    }

}
