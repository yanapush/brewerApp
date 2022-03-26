package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.CoffeeRepository;
import com.yanapush.BrewerApp.entity.Coffee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CoffeeServiceImpl implements CoffeeService {

    @NonNull
    CoffeeRepository coffeeRepository;

    @Override
    public Coffee getCoffee(int id) {
        return coffeeRepository.findById(id).orElseThrow(() -> new BadCredentialsException(MessageConstants.ERROR_GETTING));
    }

    @Override
    public List<Coffee> getCoffee() {
        return coffeeRepository.findAll();
    }

    @Override
    public boolean addCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee) == coffee;
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
