package com.once.triangleproject.rest.mapper;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.rest.config.BaseMapperConfig;
import com.once.triangleproject.rest.dto.TriangleHistoryResponse;
import com.once.triangleproject.rest.dto.TriangleRequest;
import com.once.triangleproject.rest.dto.TriangleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface TriangleMapper {

    @Mapping(target = "category", expression = "java(category)")
    TriangleResponse triangleToTriangleResponse(Triangle triangle, String category);

    Triangle triangleRequestToTriangle(TriangleRequest triangleRequest);

    TriangleHistoryResponse triangleInfoToTriangleHistoryResponse(Triangle triangleInfo);

    List<TriangleHistoryResponse> triangleInfoListToTriangleHistoryResponseList(List<Triangle> triangleInfo);
}
