package com.once.triangleproject.mysql.adapter;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.core.port.out.TriangleMysqlService;
import com.once.triangleproject.mysql.entity.TriangleEntity;
import com.once.triangleproject.mysql.mapper.TriangleEntityMapper;
import com.once.triangleproject.mysql.repository.TriangleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class TriangleMysqlServiceAdapter implements TriangleMysqlService {

    private final TriangleRepository triangleRepository;
    private final TriangleEntityMapper triangleEntityMapper;

    public TriangleMysqlServiceAdapter(TriangleRepository triangleRepository, TriangleEntityMapper triangleEntityMapper) {
        this.triangleRepository = triangleRepository;
        this.triangleEntityMapper = triangleEntityMapper;
    }

    @Override
    public Triangle insert(Triangle triangle) {
        TriangleEntity savedTriangle = triangleRepository.save(triangleEntityMapper.triangleToTriangleEntity(triangle));
        log.debug("Saved {} into the database", savedTriangle);
        return triangleEntityMapper.triangleEntityToTriangle(savedTriangle);
    }

    @Override
    public List<Triangle> retrieveAll() {
        //Spliterator allows to transverse the data in parallel, and it's suitable for large and unordered data sets,
        // but it's not suitable for small data sets or data sets that need to be ordered.
        List<Triangle> triangleList = StreamSupport.stream(triangleRepository.findAll().spliterator(), false)
                .map(triangleEntityMapper::triangleEntityToTriangle)
                .collect(Collectors.toList());
        log.debug("Retrieve {} records from the database", triangleList.size());
        return triangleList;
    }
}
