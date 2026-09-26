package com.hospital.system.hospitalmanagementsystem.controller;

import com.hospital.system.hospitalmanagementsystem.dto.AppointmentCreationDto;
import com.hospital.system.hospitalmanagementsystem.dto.AppointmentResponseDto;
import com.hospital.system.hospitalmanagementsystem.entity.Appointment;
import com.hospital.system.hospitalmanagementsystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/patient/add/appoint")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody AppointmentCreationDto appointmentCreationDto){
        AppointmentResponseDto response=appointmentService.createAppointment(appointmentCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/appointment/remove/{id}")
    public ResponseEntity<Boolean> removeAppointment(@PathVariable long id){
        boolean response=appointmentService.removeAppointment(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/appointment/get/{userId}/{role}")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointment(
            @PathVariable long userId,
            @PathVariable String role
    ) {

        List<AppointmentResponseDto> appointments =
                appointmentService.getAllAppointment(
                        userId,
                        role
                );


        return ResponseEntity.ok(appointments);
    }
    @PatchMapping("/appointment/update/{appintId}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@RequestBody AppointmentCreationDto appointmentCreationDto,@PathVariable long appintId){
        AppointmentResponseDto response=appointmentService.updateAppointment(appointmentCreationDto,appintId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
