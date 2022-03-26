package com.yanapush.BrewerApp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Step implements Serializable {
    private String start_second;
    private String step_name;
    private String duration;
    private int water_volume;
    private String description;
}
