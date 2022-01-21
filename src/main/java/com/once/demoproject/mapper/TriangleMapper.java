package com.once.demoproject.mapper;

import com.once.demoproject.config.BaseMapperConfig;
import com.once.demoproject.dto.TriangleHistoryResponse;
import com.once.demoproject.dto.TriangleRequest;
import com.once.demoproject.dto.TriangleResponse;
import com.once.demoproject.model.Triangle;
import com.once.demoproject.model.TriangleInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapstruct main interface that declares multiple mapping methods to map triangle related entities.
 * Uses {@link BaseMapperConfig} configuration class as the configuration class.
 */
@Mapper(config = BaseMapperConfig.class)
public interface TriangleMapper {


    Triangle triangleRequestToTriangle(TriangleRequest triangleRequest);

    @Mapping(target = "category", expression = "java(category)")
    TriangleResponse triangleToTriangleResponse(Triangle triangle, String category);

    TriangleHistoryResponse triangleInfoToTriangleHistoryResponse(TriangleInfo triangleInfo);

    List<TriangleHistoryResponse> triangleInfoListToTriangleHistoryResponseList(List<TriangleInfo> triangleInfo);
}
