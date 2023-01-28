package com.once.triangleproject.rest.factory;

import com.once.triangleproject.rest.dto.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

class ErrorResponseFactoryTest {

    private ErrorResponseFactoryImpl errorResponseFactory;
    private Error error;


    @BeforeEach
    void setup() {
        errorResponseFactory = new ErrorResponseFactoryImpl();
        error = Error.builder()
                .message("test")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void create_withHttpStatusAndErrorBody_returnsResponseEntity() {

        ResponseEntity<Error> result = errorResponseFactory.create(HttpStatus.OK, error);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(error, result.getBody());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}