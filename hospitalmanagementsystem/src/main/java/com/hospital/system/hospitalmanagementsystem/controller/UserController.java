package com.hospital.system.hospitalmanagementsystem.controller;

import com.hospital.system.hospitalmanagementsystem.dto.*;
import com.hospital.system.hospitalmanagementsystem.entity.Tasks;
import com.hospital.system.hospitalmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/task/add")
    public ResponseEntity<TaskResponseDto> addTask(
            @RequestBody TaskRequestDto taskRequestDto
            ) {

        TaskResponseDto response =
                userService.addTasks(taskRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/emp/update/task/{id}")
    public ResponseEntity<Boolean> updateTaskByEmpl(
            @RequestBody TaskUpdateDto taskUpdateDto,
            @PathVariable long id) {

        boolean response = userService.UpdateTaskByEmpl(taskUpdateDto,id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/task/remove/{taskId}/{userId}")
    public ResponseEntity<Boolean> removeTasks(
            @PathVariable long taskId,
            @PathVariable long userId) {

        boolean response =
                userService.removeTasks(taskId, userId);

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/task/update/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTasks(
            @RequestBody TaskUpdateDto taskUpdateDto,
            @PathVariable long taskId) {

        TaskResponseDto response =
                userService.updateTask(taskUpdateDto, taskId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/any/get/tasks/appointment/{appointmentId}")
    public ResponseEntity<List<TaskResponseDto>> getAppointmentTasks(
            @PathVariable long appointmentId
    ){
        List<TaskResponseDto> response=userService.getAppointmentTasks(appointmentId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/any/update/user/{userId}")
    public ResponseEntity<UserDtoResponse> updateUser(
            @RequestBody UserDto userDto,
            @PathVariable long userId) {

        UserDtoResponse response =
                userService.updateUser(userDto, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto request) {

        LoginResponseDto response =
                userService.login(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/any/user/{id}")
    public ResponseEntity<UserDtoResponse> getUser(
            @PathVariable long id) {

        UserDtoResponse response =
                userService.getUser(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/emp/get/tasks/{userId}")
    public ResponseEntity<List<TaskResponseDto>> getTasks(
            @PathVariable long userId) {

        List<TaskResponseDto> response =
                userService.getTasks(userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/any/get/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getDoctor() {

        List<DoctorResponseDto> response =
                userService.getDoctor();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/admin/get/tasks")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(){
        List<TaskResponseDto> response=userService.getAllTasks();
        return ResponseEntity.ok(response);
    }
}