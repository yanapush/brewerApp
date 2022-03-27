package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.entity.Coffee;
import com.yanapush.BrewerApp.service.CoffeeServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<?> getCoffee(@RequestParam(required = false) Integer id) {
        log.info("got request to get coffee with id=" + id);
        return (id == null) ? ResponseEntity.ok(coffeeServiceImpl.getCoffee()) : ResponseEntity.ok(coffeeServiceImpl.getCoffee(id));
    }

    @PostMapping
    public ResponseEntity<?> addCoffee(@Valid @RequestBody Coffee coffee) {
        log.info("got request to add coffee " + coffee.toString());
        return (coffeeServiceImpl.addCoffee(coffee)) ? ResponseEntity.ok(MessageConstants.SUCCESS_ADDING) : new ResponseEntity<>(MessageConstants.ERROR_ADDING, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCoffee(@RequestParam Integer id) {
        log.info("got request to delete coffee with id=" + id);
        return (coffeeServiceImpl.deleteCoffee(id)) ? ResponseEntity.ok(MessageConstants.SUCCESS_DELETIG) : new ResponseEntity<>(MessageConstants.IS_FORBIDDEN, HttpStatus.FORBIDDEN);
    }
}
