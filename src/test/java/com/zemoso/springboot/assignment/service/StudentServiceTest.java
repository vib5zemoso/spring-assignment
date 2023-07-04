package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.DTO.StudentDTO;
import com.zemoso.springboot.assignment.entity.Department;
import com.zemoso.springboot.assignment.entity.Student;
import com.zemoso.springboot.assignment.repository.DepartmentRepository;
import com.zemoso.springboot.assignment.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private StudentService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_ReturnsListOfEmployees() {
        // Arrange
        List<Student> employeeList = new ArrayList<>();
        employeeList.add(new Student(1L, "John", "Doe", "john.doe@example.com", new Department()));
        employeeList.add(new Student(2L, "Jane", "Smith", "jane.smith@example.com", new Department()));

        when(employeeRepository.findAll()).thenReturn(employeeList);

        // Act
        List<StudentDTO> result = employeeService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById_ExistingId_ReturnsEmployeeDTO() {
        // Arrange
        Long id = 1L;
        Student employee = new Student(id, "John", "Doe", "john.doe@example.com", new Department());

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Act
        StudentDTO result = employeeService.getStudentById(id);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findById(id);
    }

    @Test
    void getEmployeeById_NonexistentId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> employeeService.getStudentById(id));
        // Verify that the repository method was called
        verify(employeeRepository, times(1)).findById(id);
    }
}
