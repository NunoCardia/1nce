package com.once.demoproject.service;

import com.once.demoproject.model.Triangle;
import com.once.demoproject.model.TriangleInfo;
import com.once.demoproject.model.TriangleType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for the necessary Triangle methods.
 */
public interface TriangleService {

    /**
     * Triangle classification based on the sides provided in the {@link Triangle} model. A triangle has 3 possible
     * classifications: <br>
     * Equilateral - all sides have the same length<br>
     * Isosceles - two sides have the same length (first and second sides, first and third sides or second and third sides).<br>
     * Scalene - all sides have different lengths.<br>
     * The result of the classification is saved in the database in the form of {@link TriangleInfo} that contains the triangle type
     * {@link TriangleType} and the time as {@link LocalDateTime} that the classification was made.
     *
     * @param triangle the validated triangle model containing the sides.
     * @return the type of triangle.
     */
    String gradeTriangle(Triangle triangle);

    /**
     * Retrieves the triangle classification history that is present in the database.
     *
     * @return list of {@link TriangleInfo}.
     */
    List<TriangleInfo> fetchHistory();

}
