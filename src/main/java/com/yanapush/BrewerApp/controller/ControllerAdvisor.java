package com.yanapush.BrewerApp.controller;

import com.yanapush.BrewerApp.exception.CoffeeNotFoundException;
import com.yanapush.BrewerApp.exception.RecipeNotFoundException;
import com.yanapush.BrewerApp.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler({CoffeeNotFoundException.class, UserNotFoundException.class, RecipeNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            RuntimeException ex, WebRequest request) {
        log.error("Entity wasn't found. Finished with message: " + ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}