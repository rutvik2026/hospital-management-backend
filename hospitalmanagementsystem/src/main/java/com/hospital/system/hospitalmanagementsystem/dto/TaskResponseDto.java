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
public class TaskResponseDto {

    private long id;

    private String taskName;

    private long userId;

    private String userName;

    private long serviceId;

    private String serviceName;

    private long appointmentId;

    private TaskEnum status;

    private String result;
}