package com.yanapush.BrewerApp;

import com.yanapush.BrewerApp.coffee.Coffee;
import com.yanapush.BrewerApp.coffee.CoffeeServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
//@RequestMapping("/coffee")
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {

//    @NonNull
//    CoffeeServiceImpl coffeeServiceImpl;

    @GetMapping
    public ResponseEntity<?> getCoffee(@RequestParam(required = false) Integer id) {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

//    @PostMapping
//    public void addCoffee(@Valid @RequestBody Coffee coffee) {
//        coffeeServiceImpl.addCoffee(coffee);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<?> deleteCoffee(@RequestParam Integer id) {
//        return coffeeServiceImpl.deleteCoffee(id);
//    }
}
