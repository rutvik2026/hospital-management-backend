package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Appointment;
import com.hospital.system.hospitalmanagementsystem.entity.PrescriptionMedicines;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisRequestDto {
    private Appointment appointment;

    private String discription;

    private String prescription;

    private List<PrescriptionMedicines> medicines;
}
