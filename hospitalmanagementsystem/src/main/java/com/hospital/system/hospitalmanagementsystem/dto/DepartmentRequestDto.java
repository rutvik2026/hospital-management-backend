package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Doctor;
import com.hospital.system.hospitalmanagementsystem.entity.Services;
import com.hospital.system.hospitalmanagementsystem.entity.User;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestDto {

    private String deptName;

    private User headOfDept;

    private List<String> deptRole;

    private List<Services> services;
   // private List<Doctor> doctors;
}
