package com.once.demoproject.service;

import com.once.demoproject.model.Triangle;
import com.once.demoproject.model.TriangleInfo;
import com.once.demoproject.model.TriangleInfo.TriangleInfoBuilder;
import com.once.demoproject.model.TriangleType;
import com.once.demoproject.repository.TriangleInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class TriangleServiceImpl implements TriangleService {

    private final TriangleInfoRepository triangleInfoRepository;

    public TriangleServiceImpl(TriangleInfoRepository triangleInfoRepository) {
        this.triangleInfoRepository = triangleInfoRepository;
    }

    @Override
    public String gradeTriangle(Triangle triangle) {

        BigDecimal firstSide = triangle.getFirstSide();
        BigDecimal secondSide = triangle.getSecondSide();
        BigDecimal thirdSide = triangle.getThirdSide();
        TriangleInfoBuilder triangleInfoBuilder = TriangleInfo.builder()
                .firstSide(firstSide)
                .secondSide(secondSide)
                .thirdSide(thirdSide)
                .time(LocalDateTime.now());
        if (isEquilateral(firstSide, secondSide, thirdSide)) {
            triangleInfoBuilder.category(TriangleType.EQUILATERAL.getValue());
        }
        else if (isScalene(firstSide, secondSide, thirdSide)) {
            triangleInfoBuilder.category(TriangleType.SCALENE.getValue());
        } else {
            triangleInfoBuilder.category(TriangleType.ISOSCELES.getValue());
        }
        TriangleInfo triangleInfo = triangleInfoBuilder.build();
        TriangleInfo savedTriangleInfo = triangleInfoRepository.save(triangleInfo);
        log.debug("Saved {} into the database", savedTriangleInfo);
        return triangleInfo.getCategory();
    }

    @Override
    public List<TriangleInfo> fetchHistory() {
        List<TriangleInfo> triangleInfoList = triangleInfoRepository.findAll();
        log.debug("Retrieve {} records from the database", triangleInfoList.size());
        return triangleInfoList;
    }

    private boolean isEquilateral(BigDecimal firstSide, BigDecimal secondSide, BigDecimal thirdSide) {
        return firstSide.equals(secondSide) && firstSide.equals(thirdSide);
    }

    private boolean isScalene(BigDecimal firstSide, BigDecimal secondSide, BigDecimal thirdSide) {
        return !firstSide.equals(secondSide) && !firstSide.equals(thirdSide) && !secondSide.equals(thirdSide);
    }
}
