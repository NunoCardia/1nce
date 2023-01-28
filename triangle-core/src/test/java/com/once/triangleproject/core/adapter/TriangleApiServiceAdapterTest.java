package com.once.triangleproject.core.adapter;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.core.domain.TriangleType;
import com.once.triangleproject.core.port.out.TriangleMysqlService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TriangleApiServiceAdapterTest {

    @Mock
    private TriangleMysqlService triangleMysqlServiceMock;

    @InjectMocks
    private TriangleApiServiceAdapter triangleApiServiceAdapterMock;

    @Mock
    private Triangle triangleMock;

    private Triangle triangle;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        triangle = Triangle.builder()
                .firstSide(BigDecimal.valueOf(2))
                .secondSide(BigDecimal.ONE)
                .thirdSide(BigDecimal.valueOf(2))
                .build();
    }

    @Test
    void gradleTriangle_withValidTriangleSecondSameAsThird_ReturnsIsosceles() {

        Mockito.when(triangleMysqlServiceMock.insert(ArgumentMatchers.any(Triangle.class))).thenReturn(triangleMock);

        String result = triangleApiServiceAdapterMock.gradeTriangle(triangle);

        assertNotNull(result);
        assertEquals(TriangleType.ISOSCELES.getValue(), result);
        Mockito.verify(triangleMysqlServiceMock, Mockito.times(1)).insert(ArgumentMatchers.any(Triangle.class));
    }

    @Test
    void gradleTriangle_withValidTriangleFirstSameAsThird_ReturnsIsosceles() {
        triangle.setSecondSide(BigDecimal.ONE);
        triangle.setFirstSide(BigDecimal.valueOf(2));
        triangle.setThirdSide(BigDecimal.valueOf(2));
        Mockito.when(triangleMysqlServiceMock.insert(ArgumentMatchers.any(Triangle.class))).thenReturn(triangleMock);

        String result = triangleApiServiceAdapterMock.gradeTriangle(triangle);

        assertNotNull(result);
        assertEquals(TriangleType.ISOSCELES.getValue(), result);
        Mockito.verify(triangleMysqlServiceMock, Mockito.times(1)).insert(ArgumentMatchers.any(Triangle.class));
    }

    @Test
    void gradleTriangle_withValidTriangleFirstSameAsSecond_ReturnsIsosceles() {
        triangle.setThirdSide(BigDecimal.ONE);
        triangle.setFirstSide(BigDecimal.valueOf(2));
        triangle.setSecondSide(BigDecimal.valueOf(2));
        Mockito.when(triangleMysqlServiceMock.insert(ArgumentMatchers.any(Triangle.class))).thenReturn(triangleMock);

        String result = triangleApiServiceAdapterMock.gradeTriangle(triangle);

        assertNotNull(result);
        assertEquals(TriangleType.ISOSCELES.getValue(), result);
        Mockito.verify(triangleMysqlServiceMock, Mockito.times(1)).insert(ArgumentMatchers.any(Triangle.class));
    }

    @Test
    void gradleTriangle_withValidTriangle_ReturnsScalene() {
        triangle.setFirstSide(BigDecimal.valueOf(5));
        triangle.setSecondSide(BigDecimal.valueOf(7));
        triangle.setThirdSide(BigDecimal.valueOf(10));

        Mockito.when(triangleMysqlServiceMock.insert(ArgumentMatchers.any(Triangle.class))).thenReturn(triangleMock);

        String result = triangleApiServiceAdapterMock.gradeTriangle(triangle);

        assertNotNull(result);
        assertEquals(TriangleType.SCALENE.getValue(), result);
        Mockito.verify(triangleMysqlServiceMock, Mockito.times(1)).insert(ArgumentMatchers.any(Triangle.class));
    }

    @Test
    void gradleTriangle_withValidTriangle_ReturnsEquilateral() {
        triangle.setFirstSide(BigDecimal.valueOf(5));
        triangle.setSecondSide(BigDecimal.valueOf(5));
        triangle.setThirdSide(BigDecimal.valueOf(5));

        Mockito.when(triangleMysqlServiceMock.insert(ArgumentMatchers.any(Triangle.class))).thenReturn(triangleMock);

        String result = triangleApiServiceAdapterMock.gradeTriangle(triangle);

        assertNotNull(result);
        assertEquals(TriangleType.EQUILATERAL.getValue(), result);
        Mockito.verify(triangleMysqlServiceMock, Mockito.times(1)).insert(ArgumentMatchers.any(Triangle.class));
    }

    @Test
    void fetchHistory_withNoInput_returnsTriangleList() {

        Mockito.when(triangleMysqlServiceMock.retrieveAll()).thenReturn(Collections.singletonList(triangleMock));

        List<Triangle> result = triangleApiServiceAdapterMock.fetchHistory();

        assertNotNull(result);
        assertEquals(triangleMock, result.get(0));
        Mockito.verify(triangleMysqlServiceMock, Mockito.times(1)).retrieveAll();

    }
}
