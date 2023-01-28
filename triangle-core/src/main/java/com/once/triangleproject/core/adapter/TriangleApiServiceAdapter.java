package com.once.triangleproject.core.adapter;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.core.domain.TriangleType;
import com.once.triangleproject.core.port.in.TriangleApiService;
import com.once.triangleproject.core.port.out.TriangleMysqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class TriangleApiServiceAdapter implements TriangleApiService {
    private final TriangleMysqlService triangleMysqlService;

    public TriangleApiServiceAdapter(@Qualifier("triangleMysqlServiceAdapter") TriangleMysqlService mysqlService) {
        this.triangleMysqlService = mysqlService;
    }


    @Override
    public String gradeTriangle(Triangle triangle) {

        BigDecimal firstSide = triangle.getFirstSide();
        BigDecimal secondSide = triangle.getSecondSide();
        BigDecimal thirdSide = triangle.getThirdSide();
        Triangle domainTriangle = Triangle.builder()
                .firstSide(firstSide)
                .secondSide(secondSide)
                .thirdSide(thirdSide)
                .category(determineTriangleType(firstSide, secondSide, thirdSide).getValue())
                .build();
        triangleMysqlService.insert(domainTriangle);
        return domainTriangle.getCategory();
    }

    @Override
    public List<Triangle> fetchHistory() {
        return triangleMysqlService.retrieveAll();
    }

    private TriangleType determineTriangleType(BigDecimal firstSide, BigDecimal secondSide, BigDecimal thirdSide) {
        if (firstSide.compareTo(secondSide) == 0 && secondSide.compareTo(thirdSide) == 0) {
            return TriangleType.EQUILATERAL;
        } else if (firstSide.compareTo(secondSide) == 0 || secondSide.compareTo(thirdSide) == 0 || firstSide.compareTo(thirdSide) == 0) {
            return TriangleType.ISOSCELES;
        } else {
            return TriangleType.SCALENE;
        }
    }
}
