package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.enums.Gender;
import com.hospital.system.hospitalmanagementsystem.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String password;
    private String email;
    private Role role;
    private  String phone;
    private int age;
    private int weight;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String specialization;
}
