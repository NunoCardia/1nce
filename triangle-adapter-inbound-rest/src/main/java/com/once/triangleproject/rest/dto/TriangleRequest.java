package com.once.triangleproject.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TriangleRequest {

    @NotNull(message = "Side cannot be null")
    @Positive(message = "Side value must be higher than zero")
    @JsonProperty("firstSide")
    private BigDecimal firstSide;

    @NotNull(message = "Side cannot be null")
    @Positive(message = "Side value must be higher than zero")
    @JsonProperty("secondSide")
    private BigDecimal secondSide;

    @NotNull(message = "Side cannot be null")
    @Positive(message = "Side value must be higher than zero")
    @JsonProperty("thirdSide")
    private BigDecimal thirdSide;
}
