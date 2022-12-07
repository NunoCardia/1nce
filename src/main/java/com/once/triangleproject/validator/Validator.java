package com.once.demoproject.validator;

import com.once.demoproject.exception.InvalidTriangleException;

/**
 * Interface for generic validation of objects.
 */
public interface Validator<T> {

    /**
     * Method that validates a given object.
     * @param model the model to be validated.
     */
    void validate(T model) throws InvalidTriangleException;
}
