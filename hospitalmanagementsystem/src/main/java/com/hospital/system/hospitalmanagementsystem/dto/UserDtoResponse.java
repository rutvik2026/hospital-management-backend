package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.enums.Gender;
import com.hospital.system.hospitalmanagementsystem.enums.Role;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {
    private  long id;
    private String name;
    private String email;
    private Role role;
    private  String phone;
    private Gender gender;
    private Integer age;
    private Integer weight;
    private LocalDate dateOfBirth;
}
