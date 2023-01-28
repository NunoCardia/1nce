package com.once.triangleproject.rest.controller;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.rest.dto.TriangleHistoryResponse;
import com.once.triangleproject.rest.dto.TriangleRequest;
import com.once.triangleproject.rest.dto.TriangleResponse;
import com.once.triangleproject.rest.exception.InvalidTriangleException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Triangle Classification API Controller.
 */
@Api(value = "Triangle", tags = "Triangle API")
public interface TriangleApi {

    /**
     * Controller method that handles the triangle classification.
     * The given DTO is validated and mapped into a {@link Triangle} model, the classification is obtained
     * and the response is converted to a {@link TriangleResponse} DTO model.
     *
     * @param triangleRequestDto the triangle request with sides
     * @return triangle response {@link TriangleResponse}
     */

    @ApiOperation(value = "Triangle Classification", nickname = "classifyTriangle",
            notes = "This operation classifies a triangle", response = TriangleResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = TriangleResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/verify", produces = {"application/json"}, consumes = {
            "application/json"})
    ResponseEntity<TriangleResponse> classifyTriangle(
            @ApiParam(value = "The triangle sides values", required = true) @Valid @RequestBody
            TriangleRequest triangleRequestDto) throws InvalidTriangleException;

    /**
     * Retrieves the triangle classification history from the database.
     * The history is obtained from the service class and mapped to a {@link TriangleHistoryResponse} DTO.
     *
     * @return list of {@link TriangleHistoryResponse}
     */
    @ApiOperation(value = "Triangle Classification", nickname = "classifyTriangle",
            notes = "This operation classifies a triangle", response = TriangleResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = TriangleResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(produces = {"application/json"})
    ResponseEntity<List<TriangleHistoryResponse>> fetchClassificationHistory();
}
