package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Diagnosis;
import com.hospital.system.hospitalmanagementsystem.entity.Doctor;
import com.hospital.system.hospitalmanagementsystem.entity.Patient;
import com.hospital.system.hospitalmanagementsystem.enums.AppointmentType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCreationDto {
    private AppointmentType appointmentType;
    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private Doctor doctor;

    private Patient patient;
}
