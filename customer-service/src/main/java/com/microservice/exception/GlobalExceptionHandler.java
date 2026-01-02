package com.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(CustomerNotFoundException ex) {

        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", LocalDateTime.now());
        err.put("status", HttpStatus.NOT_FOUND.value());
        err.put("error", "Customer Not Found");
        err.put("message", ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleExists(CustomerAlreadyExistsException ex) {

        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", LocalDateTime.now());
        err.put("status", HttpStatus.CONFLICT.value());
        err.put("error", "Customer Already Exists");
        err.put("message", ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception ex) {

        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", LocalDateTime.now());
        err.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        err.put("error", "Server Error");
        err.put("message", ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
