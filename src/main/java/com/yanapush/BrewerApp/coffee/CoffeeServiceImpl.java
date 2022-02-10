package com.yanapush.BrewerApp.coffee;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {

    @NonNull
    CoffeeRepository coffeeRepository;

    @Override
    public Coffee getCoffee(int id) {
        return coffeeRepository.findById(id).orElse(null);
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
    public boolean deleteCoffee(int id) {
        if (coffeeRepository.existsById(id)) {
            coffeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
