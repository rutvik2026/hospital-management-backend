package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Diagnosis;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMedicineRequest {

    private String medicineName;
    private String dosage;
    private String frequency;
    private String instructions;
    private int durationDays;
    private Diagnosis diagnosis;
}
