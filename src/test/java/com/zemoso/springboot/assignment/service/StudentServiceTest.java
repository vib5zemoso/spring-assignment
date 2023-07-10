package com.zemoso.springboot.assignment.service;
import com.zemoso.springboot.assignment.dto.StudentDTO;
import com.zemoso.springboot.assignment.entity.Department;
import com.zemoso.springboot.assignment.entity.Student;
import com.zemoso.springboot.assignment.repository.DepartmentRepository;
import com.zemoso.springboot.assignment.repository.StudentRepository;
import com.zemoso.springboot.assignment.service.StudentService;
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
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents_ReturnsListOfStudents() {
        // Arrange
        List<Student> students = Arrays.asList(
                new Student(1L, "John", "Doe", "john.doe@example.com", new Department()),
                new Student(2L, "Jane", "Smith", "jane.smith@example.com", new Department())
        );
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<StudentDTO> result = studentService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        // Verify that the repository method was called
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_ExistingId_ReturnsStudentDTO() {
        // Arrange
        Long id = 1L;
        Student student = new Student(id, "John", "Doe", "john.doe@example.com", new Department());

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // Act
        StudentDTO result = studentService.getStudentById(id);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        // Verify that the repository method was called
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void getStudentById_NonexistentId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> studentService.getStudentById(id));
        // Verify that the repository method was called
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void createStudent_ValidInput_ReturnsCreatedStudentDTO() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO(null, "John", "Doe", "john.doe@example.com", 1L);
        Department department = new Department(1L, "Computer Science", "Address 1", "CS-001");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student savedStudent = invocation.getArgument(0);
            savedStudent.setId(1L);
            return savedStudent;
        });

        // Act
        StudentDTO result = studentService.createStudent(studentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals(1L, result.getDepartmentId());
        // Verify that the repository methods were called
        verify(departmentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_ValidInput_ReturnsUpdatedStudentDTO() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO(1L, "John", "Smith", "john.smith@example.com", 1L);
        Student existingStudent = new Student(1L, "John", "Doe", "john.doe@example.com", new Department());
        Department department = new Department(1L, "Computer Science", "Address 1", "CS-001");

        when(studentRepository.findById(studentDTO.getId())).thenReturn(Optional.of(existingStudent));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        // Act
        StudentDTO result = studentService.updateStudent(studentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("john.smith@example.com", result.getEmail());
        assertEquals(1L, result.getDepartmentId());
        // Verify that the repository methods were called
        verify(studentRepository, times(1)).findById(studentDTO.getId());
        verify(departmentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void deleteStudent_ExistingId_DeletesStudent() {
        // Arrange
        Long studentId = 1L;

        // Act
        studentService.deleteStudent(studentId);

        // Verify that the repository method was called
        verify(studentRepository, times(1)).deleteById(studentId);
    }
}
