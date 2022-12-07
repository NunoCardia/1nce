package com.once.demoproject.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TriangleType {
    EQUILATERAL("equilateral"),
    ISOSCELES("isosceles"),
    SCALENE("scalene");

    @Getter
    private final String value;

    TriangleType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
