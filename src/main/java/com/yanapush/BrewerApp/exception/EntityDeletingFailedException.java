package com.yanapush.BrewerApp.exception;

public class EntityDeletingFailedException extends RuntimeException {
    public EntityDeletingFailedException(String msg) {
        super(msg);
    }
}
