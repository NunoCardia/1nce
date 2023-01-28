package com.once.triangleproject.rest.exception;

/**
 * Exception that is thrown when the sides do not form a triangle.
 *
 */
public class InvalidTriangleException extends RuntimeException {

    public InvalidTriangleException() {
        super("Input given is not a valid triangle");
    }
}
