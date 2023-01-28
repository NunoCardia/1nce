package com.once.triangleproject.mysql.repository;

import com.once.triangleproject.mysql.entity.TriangleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for generic CRUD operations on a repository for the {@link TriangleEntity} entity.
 */
@Repository
public interface TriangleRepository extends CrudRepository<TriangleEntity, String> {

}
