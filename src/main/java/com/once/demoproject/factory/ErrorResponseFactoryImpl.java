package com.once.demoproject.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseFactoryImpl implements ErrorResponseFactory {
    @Override
    public <T> ResponseEntity<T> create(HttpStatus status, T body) {
        return ResponseEntity.status(status).body(body);
    }
}
