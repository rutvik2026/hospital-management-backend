package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Diagnosis;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMedicineResponseDto {
    private long id;
    private String medicineName;
    private String instructions;
    private long diagnosisId;
    private String dosage;
    private String frequency;
    private int durationDays;
}
