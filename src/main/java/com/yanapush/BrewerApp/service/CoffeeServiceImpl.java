package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.dao.CoffeeRepository;
import com.yanapush.BrewerApp.entity.Coffee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    CoffeeRepository coffeeRepository;

    @Override
    public Coffee getCoffee(String name) {
        return coffeeRepository.findById(name).orElse(null);
    }

    @Override
    public List<Coffee> getCoffee() {
        return coffeeRepository.findAll();
    }

    @Override
    public void addCoffee(Coffee coffee) {
        coffeeRepository.save(coffee);
    }

    @Override
    public boolean deleteCoffee(String name) {
        if (coffeeRepository.existsById(name)) {
            coffeeRepository.deleteById(name);
            return true;
        }
        return false;
    }
}
