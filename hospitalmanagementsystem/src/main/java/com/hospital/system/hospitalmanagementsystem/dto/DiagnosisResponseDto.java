package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.Appointment;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisResponseDto {
    private long id;
    private String prescription;
    private Appointment appointment;
    private long appointmentId;
    private String discription;

}
