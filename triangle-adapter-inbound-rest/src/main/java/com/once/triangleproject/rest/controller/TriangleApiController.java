package com.once.triangleproject.rest.controller;

import com.once.triangleproject.core.adapter.TriangleApiServiceAdapter;
import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.core.port.in.TriangleApiService;
import com.once.triangleproject.rest.dto.TriangleHistoryResponse;
import com.once.triangleproject.rest.dto.TriangleRequest;
import com.once.triangleproject.rest.dto.TriangleResponse;
import com.once.triangleproject.rest.exception.InvalidTriangleException;
import com.once.triangleproject.rest.mapper.TriangleMapper;
import com.once.triangleproject.rest.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/triangle-api/v1/triangles")
public class TriangleApiController implements TriangleApi {

    private final TriangleApiService triangleService;
    private final TriangleMapper triangleMapper;
    private final Validator<TriangleRequest> validator;

    public TriangleApiController(TriangleApiServiceAdapter triangleService, TriangleMapper triangleMapper, Validator<TriangleRequest> validator) {
        this.triangleService = triangleService;
        this.triangleMapper = triangleMapper;
        this.validator = validator;
    }


    @Override
    @PostMapping(value = "/verify")
    public ResponseEntity<TriangleResponse> classifyTriangle(@Valid TriangleRequest triangleRequestDto) throws InvalidTriangleException {
        validator.validate(triangleRequestDto);
        Triangle triangle = triangleMapper.triangleRequestToTriangle(triangleRequestDto);
        return new ResponseEntity<>(triangleMapper.triangleToTriangleResponse(triangle, triangleService.gradeTriangle(triangle)), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TriangleHistoryResponse>> fetchClassificationHistory() {
        return new ResponseEntity<>(triangleMapper.triangleInfoListToTriangleHistoryResponseList(triangleService.fetchHistory()), HttpStatus.OK);
    }
}
