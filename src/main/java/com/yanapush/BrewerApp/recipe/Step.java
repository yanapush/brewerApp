package com.yanapush.BrewerApp.recipe;

import lombok.Data;

import java.io.Serializable;

@Data
public class Step implements Serializable {
    private String start_second;
    private String duration;
    private int water_volume;
    private String description;
}
