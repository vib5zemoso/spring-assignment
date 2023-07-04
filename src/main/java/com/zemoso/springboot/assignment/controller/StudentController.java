package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.DTO.StudentDTO;
import com.zemoso.springboot.assignment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // add mapping for GET /departments
    @GetMapping("/")
    public ResponseEntity<List<StudentDTO>> getAllStudent() {
        List<StudentDTO> studentDTOS = studentService.getAllStudents();
        return ResponseEntity.ok(studentDTOS);
    }

    // add mapping for GET /departments/{departmentId}
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.getStudentById(id);
        return ResponseEntity.ok(studentDTO);
    }

    // add mapping for POST /departments - add new department
    @PostMapping("/")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createStudentDTO = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(createStudentDTO);
    }

    // add mapping for UPDATE /departments/{departmentId} - update existing departments
    @PutMapping("/")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    // add mapping for DELETE /departments/{departmentId} - delete departments by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudente(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
