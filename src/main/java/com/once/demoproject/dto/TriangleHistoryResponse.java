package com.once.demoproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TriangleHistoryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstSide")
    private BigDecimal firstSide;

    @JsonProperty("secondSide")
    private BigDecimal secondSide;

    @JsonProperty("thirdSide")
    private BigDecimal thirdSide;

    @JsonProperty("category")
    private String category;

    @JsonProperty("timestamp")
    private LocalDateTime time;
}
