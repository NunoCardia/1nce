package com.once.triangleproject.mysql.mapper;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.mysql.config.BaseMapperConfig;
import com.once.triangleproject.mysql.entity.TriangleEntity;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface TriangleEntityMapper {

    TriangleEntity triangleToTriangleEntity(Triangle triangle);
    Triangle triangleEntityToTriangle(TriangleEntity triangle);
}
