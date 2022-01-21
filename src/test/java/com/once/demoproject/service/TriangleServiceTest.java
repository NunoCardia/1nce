package com.once.demoproject.service;

import com.once.demoproject.model.Triangle;
import com.once.demoproject.model.TriangleInfo;
import com.once.demoproject.model.TriangleType;
import com.once.demoproject.repository.TriangleInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

class TriangleServiceTest {

    @Mock
    private TriangleInfoRepository triangleInfoRepositoryMock;

    @InjectMocks
    private TriangleServiceImpl triangleServiceMock;

    @Mock
    private TriangleInfo triangleInfoMock;

    private Triangle triangle;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        triangle = new Triangle();
        triangle.setFirstSide(BigDecimal.ONE);
        triangle.setSecondSide(BigDecimal.valueOf(2));
        triangle.setThirdSide(BigDecimal.valueOf(2));
    }

    @Test
    void gradleTriangle_withValidTriangleSecondSameAsThird_ReturnsIsosceles() {

        Mockito.when(triangleInfoRepositoryMock.save(ArgumentMatchers.any(TriangleInfo.class))).thenReturn(triangleInfoMock);

        String result = triangleServiceMock.gradeTriangle(triangle);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TriangleType.ISOSCELES.getValue(), result);
        Mockito.verify(triangleInfoRepositoryMock, Mockito.times(1)).save(ArgumentMatchers.any(TriangleInfo.class));
    }

    @Test
    void gradleTriangle_withValidTriangleFirstSameAsThird_ReturnsIsosceles() {
        triangle.setSecondSide(BigDecimal.ONE);
        triangle.setFirstSide(BigDecimal.valueOf(2));
        triangle.setThirdSide(BigDecimal.valueOf(2));
        Mockito.when(triangleInfoRepositoryMock.save(ArgumentMatchers.any(TriangleInfo.class))).thenReturn(triangleInfoMock);

        String result = triangleServiceMock.gradeTriangle(triangle);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TriangleType.ISOSCELES.getValue(), result);
        Mockito.verify(triangleInfoRepositoryMock, Mockito.times(1)).save(ArgumentMatchers.any(TriangleInfo.class));
    }

    @Test
    void gradleTriangle_withValidTriangleFirstSameAsSecond_ReturnsIsosceles() {
        triangle.setThirdSide(BigDecimal.ONE);
        triangle.setFirstSide(BigDecimal.valueOf(2));
        triangle.setSecondSide(BigDecimal.valueOf(2));
        Mockito.when(triangleInfoRepositoryMock.save(ArgumentMatchers.any(TriangleInfo.class))).thenReturn(triangleInfoMock);

        String result = triangleServiceMock.gradeTriangle(triangle);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TriangleType.ISOSCELES.getValue(), result);
        Mockito.verify(triangleInfoRepositoryMock, Mockito.times(1)).save(ArgumentMatchers.any(TriangleInfo.class));
    }

    @Test
    void gradleTriangle_withValidTriangle_ReturnsScalene() {
        triangle.setFirstSide(BigDecimal.valueOf(5));
        triangle.setSecondSide(BigDecimal.valueOf(7));
        triangle.setThirdSide(BigDecimal.valueOf(10));

        Mockito.when(triangleInfoRepositoryMock.save(ArgumentMatchers.any(TriangleInfo.class))).thenReturn(triangleInfoMock);

        String result = triangleServiceMock.gradeTriangle(triangle);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TriangleType.SCALENE.getValue(), result);
        Mockito.verify(triangleInfoRepositoryMock, Mockito.times(1)).save(ArgumentMatchers.any(TriangleInfo.class));
    }

    @Test
    void gradleTriangle_withValidTriangle_ReturnsEquilateral() {
        triangle.setFirstSide(BigDecimal.valueOf(5));
        triangle.setSecondSide(BigDecimal.valueOf(5));
        triangle.setThirdSide(BigDecimal.valueOf(5));

        Mockito.when(triangleInfoRepositoryMock.save(ArgumentMatchers.any(TriangleInfo.class))).thenReturn(triangleInfoMock);

        String result = triangleServiceMock.gradeTriangle(triangle);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(TriangleType.EQUILATERAL.getValue(), result);
        Mockito.verify(triangleInfoRepositoryMock, Mockito.times(1)).save(ArgumentMatchers.any(TriangleInfo.class));
    }

    @Test
    void fetchHistory_withNoInput_returnsTriangleInfoList() {

        Mockito.when(triangleInfoRepositoryMock.findAll()).thenReturn(Collections.singletonList(triangleInfoMock));

        List<TriangleInfo> result = triangleServiceMock.fetchHistory();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(triangleInfoMock, result.get(0));
        Mockito.verify(triangleInfoRepositoryMock, Mockito.times(1)).findAll();

    }
}
