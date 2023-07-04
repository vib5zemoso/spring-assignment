package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.DTO.StudentDTO;
import com.zemoso.springboot.assignment.service.StudentService;
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

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllstudent_ReturnsListOfStudentDTOs() {
        // Arrange
        List<StudentDTO> studentDTOList = new ArrayList<>();
        studentDTOList.add(new StudentDTO(1L, "John", "Doe", "john.doe@example.com", 1L));
        studentDTOList.add(new StudentDTO(2L, "Jane", "Smith", "jane.smith@example.com", 1L));

        when(studentService.getAllStudents()).thenReturn(studentDTOList);

        // Act
        ResponseEntity<List<StudentDTO>> response = studentController.getAllStudent();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstName());
        assertEquals("Doe", response.getBody().get(0).getLastName());
        assertEquals("john.doe@example.com", response.getBody().get(0).getEmail());
        // Verify that the service method was called
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getStudentById_ExistingId_ReturnsStudentDTO() {
        // Arrange
        Long id = 1L;
        StudentDTO studentDTO = new StudentDTO(id, "John", "Doe", "john.doe@example.com", 1L);

        when(studentService.getStudentById(id)).thenReturn(studentDTO);

        // Act
        ResponseEntity<StudentDTO> response = studentController.getStudentById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        // Verify that the service method was called
        verify(studentService, times(1)).getStudentById(id);
    }

    @Test
    void getStudentById_NonexistentId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;

        when(studentService.getStudentById(id)).thenReturn(null);

        // Act
        ResponseEntity<StudentDTO> response = studentController.getStudentById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(studentService, times(1)).getStudentById(id);
    }
}
