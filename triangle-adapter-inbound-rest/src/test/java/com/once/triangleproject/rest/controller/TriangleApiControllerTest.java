package com.once.triangleproject.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.once.triangleproject.core.adapter.TriangleApiServiceAdapter;
import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.core.domain.TriangleType;
import com.once.triangleproject.rest.dto.TriangleHistoryResponse;
import com.once.triangleproject.rest.dto.TriangleRequest;
import com.once.triangleproject.rest.dto.TriangleResponse;
import com.once.triangleproject.rest.mapper.TriangleMapper;
import com.once.triangleproject.rest.validator.TriangleValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

class TriangleApiControllerTest {

    private static final String REQUEST_URI = "http://localhost:8080/triangle-api/v1/triangles/";

    @Mock
    private TriangleApiServiceAdapter triangleApiServiceAdapterMock;

    @Mock
    private TriangleMapper triangleMapperMock;

    @Mock
    private TriangleValidator validatorMock;

    @InjectMocks
    private TriangleApiController triangleApiControllerMock;

    @Mock
    private Triangle triangleMock;

    @Mock
    private TriangleResponse triangleResponseMock;

    @Mock
    private TriangleHistoryResponse triangleHistoryResponseMock;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TriangleRequest triangleRequest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(triangleApiControllerMock).build();
        objectMapper = new ObjectMapper();
        triangleRequest = new TriangleRequest();
        triangleRequest.setFirstSide(BigDecimal.ONE);
        triangleRequest.setSecondSide(BigDecimal.valueOf(2));
        triangleRequest.setThirdSide(BigDecimal.valueOf(2));
    }

    @Test
    void classifyTriangle_withValidRequest_returnsTriangleResponse() throws Exception {
        Mockito.when(triangleApiServiceAdapterMock.gradeTriangle(ArgumentMatchers.any(Triangle.class))).thenReturn(TriangleType.EQUILATERAL.getValue());
        Mockito.when(triangleMapperMock.triangleToTriangleResponse(ArgumentMatchers.any(Triangle.class), ArgumentMatchers.anyString())).thenReturn(triangleResponseMock);

        mockMvc.perform(MockMvcRequestBuilders.post(REQUEST_URI + "verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(triangleRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void fetchClassificationHistory_withNoInputs_returnsTriangleHistoryResponseList() throws Exception {

        Mockito.when(triangleMapperMock.triangleInfoListToTriangleHistoryResponseList(ArgumentMatchers.anyList())).thenReturn(Collections.singletonList(triangleHistoryResponseMock));
        Mockito.when(triangleApiServiceAdapterMock.fetchHistory()).thenReturn(Collections.singletonList(triangleMock));

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_URI))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}