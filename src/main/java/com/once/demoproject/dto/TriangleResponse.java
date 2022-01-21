package com.once.demoproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TriangleResponse {

    @JsonProperty("firstSide")
    private BigDecimal firstSide;

    @JsonProperty("secondSide")
    private BigDecimal secondSide;

    @JsonProperty("thirdSide")
    private BigDecimal thirdSide;

    @JsonProperty("category")
    private String category;
}
