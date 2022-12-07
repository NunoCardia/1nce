package com.once.demoproject.repository;

import com.once.demoproject.model.TriangleInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface for generic CRUD operations on a repository for the {@link TriangleInfo} entity.
 */
@Repository
public interface TriangleInfoRepository extends CrudRepository<TriangleInfo, String> {

    List<TriangleInfo> findAll();
}
