package com.once.demoproject.validator;

import com.once.demoproject.exception.InvalidTriangleException;
import com.once.demoproject.model.Triangle;
import org.springframework.stereotype.Component;

@Component
public class TriangleValidator implements Validator<Triangle> {

    /**
     * Method that validates a {@link Triangle} entity. A triangle is valid if sum of its two sides is greater
     * than the third side. If three sides are a, b and c, then three conditions should be met:<br>
     * 1: a + b > c<br>
     * 2: a + c > b<br>
     * 3: b + c > a
     * @param model the model to be validated.
     * @throws InvalidTriangleException exception if the previous conditions are not met.
     */
    @Override
    public void validate(Triangle model) throws InvalidTriangleException {
        if (model.getFirstSide().add(model.getSecondSide()).compareTo(model.getThirdSide()) <= 0 ||
                model.getFirstSide().add(model.getThirdSide()).compareTo(model.getSecondSide()) <= 0 ||
                model.getSecondSide().add(model.getThirdSide()).compareTo(model.getFirstSide()) <= 0) {
            throw new InvalidTriangleException();
        }
    }
}
