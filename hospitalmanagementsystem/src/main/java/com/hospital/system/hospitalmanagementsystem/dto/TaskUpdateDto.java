package com.hospital.system.hospitalmanagementsystem.dto;

import com.hospital.system.hospitalmanagementsystem.enums.TaskEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDto {

    private TaskEnum status;

    private String result;
}