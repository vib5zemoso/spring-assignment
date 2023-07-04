package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.DTO.DepartmentDTO;
import com.zemoso.springboot.assignment.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDepartment_ReturnsListOfDepartmentDTOs() {
        // Arrange
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        departmentDTOList.add(new DepartmentDTO(1L, "IT", "123 Street", "IT-001"));
        departmentDTOList.add(new DepartmentDTO(2L, "HR", "456 Avenue", "HR-001"));

        when(departmentService.getAllDepartments()).thenReturn(departmentDTOList);

        // Act
        ResponseEntity<List<DepartmentDTO>> response = departmentController.getAllDepartment();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("IT", response.getBody().get(0).getDepartmentName());
        assertEquals("123 Street", response.getBody().get(0).getDepartmentAddress());
        assertEquals("IT-001", response.getBody().get(0).getDepartmentCode());
        // Verify that the service method was called
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void getDepartmentById_ExistingId_ReturnsDepartmentDTO() {
        // Arrange
        Long id = 1L;
        DepartmentDTO departmentDTO = new DepartmentDTO(id, "IT", "123 Street", "IT-001");

        when(departmentService.getDepartmentById(id)).thenReturn(departmentDTO);

        // Act
        ResponseEntity<DepartmentDTO> response = departmentController.getDepartmentById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("IT", response.getBody().getDepartmentName());
        assertEquals("123 Street", response.getBody().getDepartmentAddress());
        assertEquals("IT-001", response.getBody().getDepartmentCode());
        // Verify that the service method was called
        verify(departmentService, times(1)).getDepartmentById(id);
    }

    @Test
    void getDepartmentById_NonexistentId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;

        when(departmentService.getDepartmentById(id)).thenReturn(null);

        // Act
        ResponseEntity<DepartmentDTO> response = departmentController.getDepartmentById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(departmentService, times(1)).getDepartmentById(id);
    }
}
