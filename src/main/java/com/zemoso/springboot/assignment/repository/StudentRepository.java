package com.zemoso.springboot.assignment.repository;

import com.zemoso.springboot.assignment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findDepartmentById(Long departmentId);
}
