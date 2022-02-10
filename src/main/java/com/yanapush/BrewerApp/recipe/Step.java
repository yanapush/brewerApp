package com.yanapush.BrewerApp.recipe;

import lombok.Data;

import java.io.Serializable;

@Data
public class Step implements Serializable {
    private int start_second;
    private int duration;
    private int water_volume;
    private String description;
}
