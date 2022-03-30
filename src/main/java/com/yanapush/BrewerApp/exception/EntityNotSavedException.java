package com.yanapush.BrewerApp.exception;

public class EntityNotSavedException extends RuntimeException {
    public EntityNotSavedException(String msg) {
        super(msg);
    }
}
