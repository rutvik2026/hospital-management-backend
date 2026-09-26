package com.hospital.system.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Appointment appointment;

    private String discription;

    private String prescription;

    @OneToMany(
            mappedBy = "diagnosis",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PrescriptionMedicines> medicines = new ArrayList<>();

    public void addMedicines(PrescriptionMedicines prescriptionMedicines){
        if(medicines==null){
            medicines=new ArrayList<>();

        }
        medicines.add(prescriptionMedicines);
    }
}
