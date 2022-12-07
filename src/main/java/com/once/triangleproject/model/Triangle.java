package com.once.demoproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Triangle {

    private BigDecimal firstSide;

    private BigDecimal secondSide;

    private BigDecimal thirdSide;
}
