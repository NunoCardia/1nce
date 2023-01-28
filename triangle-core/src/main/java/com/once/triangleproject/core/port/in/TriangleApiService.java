package com.once.triangleproject.core.port.in;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.core.domain.TriangleType;

import java.time.LocalDateTime;
import java.util.List;

public interface TriangleApiService {

    /**
     * Triangle classification based on the sides provided in the {@link Triangle} model. A triangle has 3 possible
     * classifications: <br>
     * Equilateral - all sides have the same length<br>
     * Isosceles - two sides have the same length (first and second sides, first and third sides or second and third sides).<br>
     * Scalene - all sides have different lengths.<br>
     * The result of the classification is saved in the database in the form of {@link Triangle} that contains the triangle type
     * {@link TriangleType} and the time as {@link LocalDateTime} that the classification was made.
     *
     * @param triangle the validated triangle model containing the sides.
     * @return the type of triangle.
     */
    String gradeTriangle(Triangle triangle);

    /**
     * Retrieves the triangle classification history that is present in the database.
     *
     * @return list of {@link Triangle}.
     */
    List<Triangle> fetchHistory();
}
