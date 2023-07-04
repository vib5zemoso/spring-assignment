package com.zemoso.springboot.assignment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private Long id;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}
