package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.CoffeeRepository;
import com.yanapush.BrewerApp.entity.Coffee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {

    @NonNull
    CoffeeRepository coffeeRepository;

    @Override
    public ResponseEntity<?> getCoffee(int id) {
        Optional<Coffee> coffee = coffeeRepository.findById(id);
        return  (coffee.equals(Optional.empty())) ? new ResponseEntity<>(MessageConstants.ERROR_GETTING, HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(coffee.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCoffee() {
        return new ResponseEntity<>(coffeeRepository.findAll(),
                HttpStatus.OK );
    }

    @Override
    public void addCoffee(Coffee coffee) {
        coffeeRepository.save(coffee);
    }

    @Override
    public ResponseEntity<?> deleteCoffee(int id) {
        if (coffeeRepository.existsById(id)) {
            coffeeRepository.deleteById(id);
            return new ResponseEntity<>(MessageConstants.SUCCESS_DELETIG, HttpStatus.OK);
        }
        return new ResponseEntity<>(MessageConstants.ERROR_GETTING, HttpStatus.NOT_FOUND);
    }
}
