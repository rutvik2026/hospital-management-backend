package com.hospital.system.hospitalmanagementsystem.service;

import com.hospital.system.hospitalmanagementsystem.dto.AppointmentCreationDto;
import com.hospital.system.hospitalmanagementsystem.dto.AppointmentResponseDto;
import com.hospital.system.hospitalmanagementsystem.entity.Appointment;

import java.util.HashMap;
import java.util.List;

public interface AppointmentService {
    public AppointmentResponseDto createAppointment(AppointmentCreationDto appointmentCreationDto);

    public boolean removeAppointment(long id);

    public List<AppointmentResponseDto> getAllAppointment(long userId,String role);

    public AppointmentResponseDto updateAppointment(AppointmentCreationDto appointmentCreationDto,long appintId);
}
