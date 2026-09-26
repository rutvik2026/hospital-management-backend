package com.hospital.system.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionMedicines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String medicineName;
    private String dosage;
    private String frequency;
    private String instructions;
    private int durationDays;
    @ManyToOne
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;
}
