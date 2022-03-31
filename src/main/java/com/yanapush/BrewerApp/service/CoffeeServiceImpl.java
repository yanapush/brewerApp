package com.yanapush.BrewerApp.service;

import com.yanapush.BrewerApp.constant.MessageConstants;
import com.yanapush.BrewerApp.dao.CoffeeRepository;
import com.yanapush.BrewerApp.entity.Coffee;
import com.yanapush.BrewerApp.exception.EntityDeletingFailedException;
import com.yanapush.BrewerApp.exception.EntityNotFoundException;
import com.yanapush.BrewerApp.exception.EntityNotSavedException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class CoffeeServiceImpl implements CoffeeService {

    @NonNull
    CoffeeRepository coffeeRepository;

    MessageConstants constants = new MessageConstants();

    @Override
    public Coffee getCoffee(int id) {
        log.info("beginning of getting coffee with id=" + id);
        return coffeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_ID, "coffee", id)));
    }

    @Override
    public List<Coffee> getCoffee() {
        log.info("beginning of getting all coffee");
        return coffeeRepository.findAll();
    }

    @Override
    public Coffee addCoffee(Coffee coffee) {
        log.info("beginning of adding coffee " + coffee.toString());
        if (coffeeRepository.save(coffee) == coffee) {
            return coffee;
        }
        throw new EntityNotSavedException(String.format(constants.ERROR_ADDING, "coffee"));
    }

    @Override
    public boolean deleteCoffee(int id) {
        log.info("beginning of deleting coffee with id=" + id);
        if (coffeeRepository.existsById(id)) {
            log.info("coffee exists");
            coffeeRepository.deleteById(id);
            if(coffeeRepository.existsById(id)) {
                throw new EntityDeletingFailedException(String.format(constants.IS_FORBIDDEN, "coffee"));
            }
            return true;
        }
        log.error("coffee with such id doesn't exists");
        throw new EntityNotFoundException(String.format(constants.ERROR_GETTING_BY_ID, "coffee", id));
    }
}
