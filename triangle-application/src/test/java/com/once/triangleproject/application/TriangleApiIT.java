package com.once.triangleproject.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.once.triangleproject.rest.dto.TriangleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@AutoConfigureMockMvc
class TriangleApiIT extends BaseTest {

    private static final String REQUEST_URI = "http://localhost:8080/triangle-api/v1/triangles/";
    private ObjectMapper objectMapper;
    private TriangleRequest triangleRequest;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        triangleRequest = new TriangleRequest();
        triangleRequest.setFirstSide(BigDecimal.ONE);
        triangleRequest.setSecondSide(BigDecimal.valueOf(2));
        triangleRequest.setThirdSide(BigDecimal.valueOf(2));
    }

    @Test
    void classifyTriangle_withValidRequest_returnsTriangleResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(REQUEST_URI + "verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(triangleRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void classifyTriangle_withValidRequest_throwsInvalidTriangleException() throws Exception {

        triangleRequest.setSecondSide(BigDecimal.ONE);

        mockMvc.perform(MockMvcRequestBuilders.post(REQUEST_URI + "verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(triangleRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void fetchClassificationHistory_withNoInputs_returnsTriangleHistoryResponseList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_URI))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
