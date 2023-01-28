package com.once.triangleproject.rest.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Interface that contain the method to create a generic Error response.
 */
public interface ErrorResponseFactory {

    /**
     * Creates a responseEntity object to be returned as an error object in case of an exception occurs.
     * @param status HTTP status code.
     * @param body object to be returned
     * @return {@link ResponseEntity} with the given object
     */
    <T> ResponseEntity<T> create(HttpStatus status, T body);
}
