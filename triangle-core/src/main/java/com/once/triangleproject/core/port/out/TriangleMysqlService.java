package com.once.triangleproject.core.port.out;

import com.once.triangleproject.core.domain.Triangle;

import java.util.List;

/**
 * The `TriangleMysqlService` interface is used to perform CRUD operations on {@link Triangle} objects in a MySQL database.
 * It provides methods for inserting a new triangle and retrieving all triangles from the database.
 *
 */
public interface TriangleMysqlService {

    /**
     * Inserts a new {@link Triangle} object into the database.
     * @param triangle the {@link Triangle} object to be inserted
     * @return the inserted {@link Triangle} object
     */
    Triangle insert(Triangle triangle);

    /**
     * Retrieves all {@link Triangle} objects from the MySQL database.
     * @return a list of all {@link Triangle} objects in the database
     */
    List<Triangle> retrieveAll();
}
