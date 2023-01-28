package com.once.triangleproject.mysql.adapter;

import com.once.triangleproject.core.domain.Triangle;
import com.once.triangleproject.mysql.entity.TriangleEntity;
import com.once.triangleproject.mysql.mapper.TriangleEntityMapper;
import com.once.triangleproject.mysql.repository.TriangleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TriangleMysqlServiceAdapterTest {

    @Mock
    private TriangleRepository triangleRepositoryMock;

    @Mock
    private TriangleEntityMapper triangleEntityMapperMock;

    @InjectMocks
    private TriangleMysqlServiceAdapter triangleMysqlServiceAdapter;

    @Mock
    private Triangle triangleMock;

    @Mock
    private TriangleEntity triangleEntityMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        triangleMysqlServiceAdapter = new TriangleMysqlServiceAdapter(triangleRepositoryMock, triangleEntityMapperMock);
    }

    @Test
    void testInsert() {
        // Configure mocks
        when(triangleEntityMapperMock.triangleToTriangleEntity(any(Triangle.class))).thenReturn(triangleEntityMock);
        when(triangleRepositoryMock.save(triangleEntityMock)).thenReturn(triangleEntityMock);
        when(triangleEntityMapperMock.triangleEntityToTriangle(triangleEntityMock)).thenReturn(triangleMock);

        // Test
        Triangle result = triangleMysqlServiceAdapter.insert(triangleMock);

        // Verify
        assertEquals(triangleMock, result);
        verify(triangleRepositoryMock).save(triangleEntityMock);
        verify(triangleEntityMapperMock).triangleToTriangleEntity(triangleMock);
        verify(triangleEntityMapperMock).triangleEntityToTriangle(triangleEntityMock);
    }

    @Test
    void testRetrieveAll() {

        when(triangleRepositoryMock.findAll()).thenReturn(Collections.singletonList(triangleEntityMock));
        when(triangleEntityMapperMock.triangleEntityToTriangle(triangleEntityMock)).thenReturn(triangleMock);

        List<Triangle> result = triangleMysqlServiceAdapter.retrieveAll();

        assertEquals(Collections.singletonList(triangleMock), result);
        verify(triangleRepositoryMock).findAll();
    }
}