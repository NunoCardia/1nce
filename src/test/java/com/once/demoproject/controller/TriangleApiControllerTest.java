package com.once.demoproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.once.demoproject.dto.TriangleHistoryResponse;
import com.once.demoproject.dto.TriangleRequest;
import com.once.demoproject.dto.TriangleResponse;
import com.once.demoproject.mapper.TriangleMapper;
import com.once.demoproject.model.Triangle;
import com.once.demoproject.model.TriangleInfo;
import com.once.demoproject.model.TriangleType;
import com.once.demoproject.service.TriangleService;
import com.once.demoproject.service.TriangleServiceImpl;
import com.once.demoproject.validator.TriangleValidator;
import com.once.demoproject.validator.Validator;
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
    private TriangleServiceImpl triangleServiceMock;

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

    @Mock
    private TriangleInfo triangleInfoMock;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TriangleRequest triangleRequest;

    @BeforeEach
    void setup(){
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

        Mockito.when(triangleMapperMock.triangleRequestToTriangle(ArgumentMatchers.any(TriangleRequest.class))).thenReturn(triangleMock);
        Mockito.when(triangleServiceMock.gradeTriangle(ArgumentMatchers.any(Triangle.class))).thenReturn(TriangleType.EQUILATERAL.getValue());
        Mockito.when(triangleMapperMock.triangleToTriangleResponse(ArgumentMatchers.any(Triangle.class), ArgumentMatchers.anyString())).thenReturn(triangleResponseMock);

        mockMvc.perform(MockMvcRequestBuilders.post(REQUEST_URI + "verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(triangleRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void fetchClassificationHistory_withNoInputs_returnsTriangleHistoryResponseList() throws Exception {

        Mockito.when(triangleMapperMock.triangleInfoListToTriangleHistoryResponseList(ArgumentMatchers.anyList())).thenReturn(Collections.singletonList(triangleHistoryResponseMock));
        Mockito.when(triangleServiceMock.fetchHistory()).thenReturn(Collections.singletonList(triangleInfoMock));

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_URI))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
