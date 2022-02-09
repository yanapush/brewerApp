package com.yanapush.BrewerApp.entity;

import java.io.Serializable;

public class Step implements Serializable {
    private int start_second;
    private int duration;
    private int water_volume;
    private String description;

    public Step() {
    }

    public Step(int start_second, int duration, int water_volume) {
        this.start_second = start_second;
        this.duration = duration;
        this.water_volume = water_volume;
    }

    public Step(int start_second, int duration, int water_volume, String description) {
        this.start_second = start_second;
        this.duration = duration;
        this.water_volume = water_volume;
        this.description = description;
    }

    public int getStart_second() {
        return start_second;
    }

    public void setStart_second(int start_second) {
        this.start_second = start_second;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getWater_volume() {
        return water_volume;
    }

    public void setWater_volume(int water_volume) {
        this.water_volume = water_volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Step{" +
                "start_second=" + start_second +
                ", water_volume=" + water_volume +
                '}';
    }
}
