package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Coffee;
import com.yanapush.BrewerApp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeRepository extends JpaRepository<Coffee, String> {
}
