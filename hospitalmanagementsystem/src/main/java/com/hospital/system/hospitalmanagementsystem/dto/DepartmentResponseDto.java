package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Services;
import com.hospital.system.hospitalmanagementsystem.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDto {
    private long id;
    private String deptName;
    private User headOfDept;
    private Long headOfDeptId;
    private String headOfDeptName;
    private List<String> deptRole;
    private List<Services> services=new ArrayList<>();
    private Integer serviceCount;
   // private Integer doctorsCount;
}
