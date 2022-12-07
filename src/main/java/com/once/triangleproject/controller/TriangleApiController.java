package com.once.demoproject.controller;

import com.once.demoproject.dto.TriangleHistoryResponse;
import com.once.demoproject.dto.TriangleRequest;
import com.once.demoproject.dto.TriangleResponse;
import com.once.demoproject.exception.InvalidTriangleException;
import com.once.demoproject.mapper.TriangleMapper;
import com.once.demoproject.model.Triangle;
import com.once.demoproject.service.TriangleService;
import com.once.demoproject.service.TriangleServiceImpl;
import com.once.demoproject.validator.TriangleValidator;
import com.once.demoproject.validator.Validator;
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

    private final TriangleService triangleService;
    private final TriangleMapper triangleMapper;
    private final Validator<Triangle> validator;

    public TriangleApiController(TriangleServiceImpl triangleService, TriangleMapper triangleMapper, TriangleValidator validator) {
        this.triangleService = triangleService;
        this.triangleMapper = triangleMapper;
        this.validator = validator;
    }

    @Override
    @PostMapping(value = "/verify")
    public ResponseEntity<TriangleResponse> classifyTriangle(@Valid TriangleRequest triangleRequestDto) throws InvalidTriangleException {
        Triangle triangle = triangleMapper.triangleRequestToTriangle(triangleRequestDto);
        validator.validate(triangle);
        return new ResponseEntity<>(triangleMapper.triangleToTriangleResponse(triangle, triangleService.gradeTriangle(triangle)), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TriangleHistoryResponse>> fetchClassificationHistory() {
        return new ResponseEntity<>(triangleMapper.triangleInfoListToTriangleHistoryResponseList(triangleService.fetchHistory()), HttpStatus.OK);
    }
}
