package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Doctor;
import com.hospital.system.hospitalmanagementsystem.entity.Patient;
import com.hospital.system.hospitalmanagementsystem.enums.AppointmentType;
import lombok.*;
import org.hibernate.boot.internal.Abstract;

import java.time.LocalDate;
import java.time.LocalTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {
    private long id;
    private AppointmentType appointmentType;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private long doctorId;
    private String doctorName;
    private  String specialization;
    private long patientId;
    private String patientName;
}
