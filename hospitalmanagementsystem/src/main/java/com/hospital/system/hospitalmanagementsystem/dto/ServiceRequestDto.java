package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Department;
import com.hospital.system.hospitalmanagementsystem.entity.Inventories;
import com.hospital.system.hospitalmanagementsystem.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDto {
    private String serviceName;

    private float serviceFee;

    private String description;

    private String result;

    private List<User> serviceUsers;

    private List<Inventories> inventories;

    private Department department;
}
