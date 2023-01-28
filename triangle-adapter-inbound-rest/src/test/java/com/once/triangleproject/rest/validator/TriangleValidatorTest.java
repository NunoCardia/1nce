package com.once.triangleproject.rest.validator;


import com.once.triangleproject.rest.dto.TriangleRequest;
import com.once.triangleproject.rest.exception.InvalidTriangleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TriangleValidatorTest {

    private TriangleValidator triangleValidator;
    private TriangleRequest triangle;

    @BeforeEach
    void setup() {
        triangleValidator = new TriangleValidator();
        triangle = new TriangleRequest();
        triangle.setFirstSide(BigDecimal.valueOf(7));
        triangle.setSecondSide(BigDecimal.valueOf(10));
        triangle.setThirdSide(BigDecimal.valueOf(5));
    }

    @Test
    void validate_withValidTriangleSides_NoReturn() {

        Assertions.assertDoesNotThrow(() -> triangleValidator.validate(triangle));

    }

    @Test
    void validate_withInValidTriangleSides_ThrowsInvalidTriangleException() {

        triangle.setThirdSide(BigDecimal.ONE);

        Assertions.assertThrows(InvalidTriangleException.class, () -> triangleValidator.validate(triangle));

    }

    @Test
    void validate_withTwoSameTriangleSides_ThrowsInvalidTriangleException() {

        triangle.setThirdSide(BigDecimal.ONE);
        triangle.setSecondSide(BigDecimal.ONE);
        triangle.setFirstSide(BigDecimal.valueOf(2));

        Assertions.assertThrows(InvalidTriangleException.class, () -> triangleValidator.validate(triangle));

    }


}