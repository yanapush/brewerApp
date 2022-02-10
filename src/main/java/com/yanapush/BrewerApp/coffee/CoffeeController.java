package com.yanapush.BrewerApp.coffee;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coffee")
@RequiredArgsConstructor
public class CoffeeController {

    @NonNull
    CoffeeServiceImpl coffeeServiceImpl;

    @GetMapping
    public ResponseEntity<?> getCoffee(@RequestParam(required = false) Integer id) {
        if (id == null) {
            return new ResponseEntity<>(coffeeServiceImpl.getCoffee(), HttpStatus.OK);
        } else {
            Coffee coffee = coffeeServiceImpl.getCoffee(id);
            return (coffee == null) ? new ResponseEntity<>(MessageConstants.ERROR_GETTING_COFFEE, HttpStatus.NOT_FOUND)
                    : new ResponseEntity<>(coffee, HttpStatus.OK);
        }
    }

    @PostMapping
    public void addCoffee(@RequestBody Coffee coffee) {
        coffeeServiceImpl.addCoffee(coffee);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCoffee(@RequestParam Integer id) {
        return (coffeeServiceImpl.deleteCoffee(id))
                ? new ResponseEntity<>(MessageConstants.SUCCESS_DELETING_DELETING, HttpStatus.OK)
                : new ResponseEntity<>(MessageConstants.ERROR_GETTING_COFFEE, HttpStatus.NOT_FOUND);
    }

}
