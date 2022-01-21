package com.once.demoproject.validator;

import com.once.demoproject.exception.InvalidTriangleException;
import com.once.demoproject.model.Triangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TriangleValidatorTest {

    private TriangleValidator triangleValidator;
    private Triangle triangle;

    @BeforeEach
    void setup() {
        triangleValidator = new TriangleValidator();
        triangle = new Triangle();
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
