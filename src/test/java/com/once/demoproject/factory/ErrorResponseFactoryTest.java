package com.once.demoproject.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import com.once.demoproject.dto.Error;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

class ErrorResponseFactoryTest {

    private ErrorResponseFactoryImpl errorResponseFactory;
    private Error error;


    @BeforeEach
    void setup(){
        errorResponseFactory = new ErrorResponseFactoryImpl();
        error = Error.builder()
                .message("test")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void create_withHttpStatusAndErrorBody_returnsResponseEntity(){

        ResponseEntity<Error> result = errorResponseFactory.create(HttpStatus.OK, error);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(error, result.getBody());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
