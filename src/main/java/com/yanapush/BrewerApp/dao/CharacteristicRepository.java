package com.yanapush.BrewerApp.dao;

import com.yanapush.BrewerApp.entity.Characteristic;
import com.yanapush.BrewerApp.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Integer>{

}
