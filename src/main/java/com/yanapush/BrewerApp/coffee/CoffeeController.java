package com.yanapush.BrewerApp.coffee;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/coffee")
@RequiredArgsConstructor
@CrossOrigin
public class CoffeeController {

    @NonNull
    CoffeeServiceImpl coffeeServiceImpl;

    @GetMapping
    public ResponseEntity<?> getCoffee(@RequestParam(required = false) Integer id) {
        return (id == null) ? coffeeServiceImpl.getCoffee() : coffeeServiceImpl.getCoffee(id);
    }

    @PostMapping
    public void addCoffee(@Valid @RequestBody Coffee coffee) {
        coffeeServiceImpl.addCoffee(coffee);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCoffee(@RequestParam Integer id) {
        return coffeeServiceImpl.deleteCoffee(id);
    }
}
