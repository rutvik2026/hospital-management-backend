package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.enums.Gender;
import com.hospital.system.hospitalmanagementsystem.enums.Role;
import jdk.jfr.Name;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDto {
    private  long id;
    private String name;
    private String email;
    private Role role;
    private  String phone;
    private Gender gender;
    private String specialization;
}
