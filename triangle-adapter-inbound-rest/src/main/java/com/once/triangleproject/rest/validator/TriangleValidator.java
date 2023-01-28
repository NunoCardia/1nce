package com.once.triangleproject.rest.validator;


import com.once.triangleproject.rest.dto.TriangleRequest;
import com.once.triangleproject.rest.exception.InvalidTriangleException;
import org.springframework.stereotype.Component;

@Component
public class TriangleValidator implements Validator<TriangleRequest> {

    /**
     * Method that validates a {@link TriangleRequest} entity. A triangle is valid if sum of its two sides is greater
     * than the third side. If three sides are a, b and c, then three conditions should be met:<br>
     * 1: a + b > c<br>
     * 2: a + c > b<br>
     * 3: b + c > a
     * @param request the request to be validated.
     * @throws InvalidTriangleException exception if the previous conditions are not met.
     */
    @Override
    public void validate(TriangleRequest request) throws InvalidTriangleException {
        if (request.getFirstSide().add(request.getSecondSide()).compareTo(request.getThirdSide()) <= 0 ||
                request.getFirstSide().add(request.getThirdSide()).compareTo(request.getSecondSide()) <= 0 ||
                request.getSecondSide().add(request.getThirdSide()).compareTo(request.getFirstSide()) <= 0) {
            throw new InvalidTriangleException();
        }
    }
}
