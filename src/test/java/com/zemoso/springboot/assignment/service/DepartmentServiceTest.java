package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.DTO.DepartmentDTO;
import com.zemoso.springboot.assignment.entity.Department;
import com.zemoso.springboot.assignment.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDepartments_ReturnsListOfDepartments() {
        // Arrange
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department(1L, "HR", "Address 1", "HR-001"));
        departmentList.add(new Department(2L, "Finance", "Address 2", "FIN-001"));

        when(departmentRepository.findAll()).thenReturn(departmentList);

        // Act
        List<DepartmentDTO> result = departmentService.getAllDepartments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("HR", result.get(0).getDepartmentName());
        assertEquals("Address 1", result.get(0).getDepartmentAddress());
        assertEquals("HR-001", result.get(0).getDepartmentCode());
        // Verify that the repository method was called
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getDepartmentById_ExistingId_ReturnsDepartmentDTO() {
        // Arrange
        Long id = 1L;
        Department department = new Department(id, "HR", "Address 1", "HR-001");

        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        // Act
        DepartmentDTO result = departmentService.getDepartmentById(id);

        // Assert
        assertNotNull(result);
        assertEquals("HR", result.getDepartmentName());
        assertEquals("Address 1", result.getDepartmentAddress());
        assertEquals("HR-001", result.getDepartmentCode());
        // Verify that the repository method was called
        verify(departmentRepository, times(1)).findById(id);
    }

    @Test
    void getDepartmentById_NonexistentId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;

        when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> departmentService.getDepartmentById(id));
        // Verify that the repository method was called
        verify(departmentRepository, times(1)).findById(id);
    }
}
