package com.yanapush.BrewerApp.coffee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoffeeRepository extends JpaRepository<Coffee, Integer> {
    List<Coffee> findAllByCountry(String country);
}
