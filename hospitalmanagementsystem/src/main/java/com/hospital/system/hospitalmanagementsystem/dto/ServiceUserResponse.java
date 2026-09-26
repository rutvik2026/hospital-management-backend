package com.hospital.system.hospitalmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUserResponse {

    private long id;

    private String name;

    private String username;

    private String email;
}