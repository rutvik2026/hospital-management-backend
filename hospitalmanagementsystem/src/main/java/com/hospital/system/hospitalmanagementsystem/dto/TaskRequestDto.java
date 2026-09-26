package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.entity.User;
import com.hospital.system.hospitalmanagementsystem.enums.TaskEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;



import com.hospital.system.hospitalmanagementsystem.enums.TaskEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {

    private String taskName;

    private TaskEnum status;

    private long appointmentId;

    private long serviceId;
}
