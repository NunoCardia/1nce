package com.once.triangleproject.core.domain;

import lombok .Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Triangle {

    private BigDecimal firstSide;
    private BigDecimal secondSide;
    private BigDecimal thirdSide;
    private String category;
}
