package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.entity.Coffee;
import com.yanapush.BrewerApp.service.CoffeeServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/coffee")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class CoffeeController {

    @NonNull
    CoffeeServiceImpl coffeeServiceImpl;

    @Autowired
    private MessageConstants constants;

    @GetMapping
    public ResponseEntity<?> getCoffee(@RequestParam(required = false) Integer id) {
        log.info("got request to get coffee with id=" + id);
        return (id == null) ? ResponseEntity.ok(coffeeServiceImpl.getCoffee()) : ResponseEntity.ok(coffeeServiceImpl.getCoffee(id));
    }

    @PostMapping
    public ResponseEntity<?> addCoffee(@Valid @RequestBody Coffee coffee) {
        log.info("got request to add coffee " + coffee.toString());
        coffeeServiceImpl.addCoffee(coffee);
        return ResponseEntity.ok(String.format(constants.SUCCESS_ADDING, "coffee"));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCoffee(@RequestParam Integer id) {
        log.info("got request to delete coffee with id=" + id);
        coffeeServiceImpl.deleteCoffee(id);
        return ResponseEntity.ok(String.format(constants.SUCCESS_DELETING, "coffee"));
    }
}
