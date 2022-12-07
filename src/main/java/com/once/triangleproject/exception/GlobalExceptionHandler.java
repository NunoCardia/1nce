package com.once.demoproject.exception;

import com.once.demoproject.dto.Error;
import com.once.demoproject.factory.ErrorResponseFactory;
import com.once.demoproject.factory.ErrorResponseFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Application Exception Handler. Exceptions thrown are processed here. If the exception that is thrown is not present
 * in this class with will be handled by Spring's ExceptionHandler.
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    public GlobalExceptionHandler(ErrorResponseFactoryImpl errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    /**
     * Handles MethodArgumentNotValidException exception that occurs when bean validation fails. It is applicable
     * to the requestBody of the /verify endpoint.
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> fields = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField())
                .collect(Collectors.toList());
        log.error(
                "Executed handleMethodArgumentNotValidException. Validation failed for request for fields {}. Further information: {} | Caused by: {} | Stack Trace: {}",
                fields, ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return errorResponseFactory.create(HttpStatus.BAD_REQUEST, create("Request validation failed for fields " + fields));
    }

    /**
     * Handles ConnectException exception that occurs when the connection to the database fails somehow.
     *
     */
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<Error> handleConnectException(ConnectException ex) {
        log.error(
                "Executed handleConnectException. Connection to the database failed. Further information: {} | Caused by: {} | Stack Trace: {}",
                ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return errorResponseFactory.create(HttpStatus.INTERNAL_SERVER_ERROR, create("Connection to the database failed"));
    }

    /**
     * Handles InvalidTriangleException exception that occurs when sides do not form a triangle.
     *
     */
    @ExceptionHandler(InvalidTriangleException.class)
    public ResponseEntity<Error> handleInvalidTriangleException(InvalidTriangleException ex) {
        log.error(
                "Executed handleConnectException. Sides do not form a triangle. Further information: {} | Caused by: {} | Stack Trace: {}",
                ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return errorResponseFactory.create(HttpStatus.BAD_REQUEST, create(ex.getMessage()));
    }

    private Error create(String message) {
        return Error.builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
