package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Patient;
import com.hospital.system.hospitalmanagementsystem.entity.Services;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoriesRequestDto {
    private String name;

    private float fee;

    private int stock;
    private Services services;
    private Patient patient;
}
