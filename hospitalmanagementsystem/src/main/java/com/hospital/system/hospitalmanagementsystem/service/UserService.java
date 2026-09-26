package com.hospital.system.hospitalmanagementsystem.service;

import com.hospital.system.hospitalmanagementsystem.dto.*;
import com.hospital.system.hospitalmanagementsystem.entity.Tasks;
import com.hospital.system.hospitalmanagementsystem.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    public UserDtoResponse addUser(UserDto userDto);

    public TaskResponseDto addTasks(TaskRequestDto taskRequestDto);
    public boolean UpdateTaskByEmpl(TaskUpdateDto taskUpdateDto,long id);

    public boolean removeTasks(long taskId, long userId);
    public TaskResponseDto updateTask(TaskUpdateDto taskRequestDto,long taskId);
    public List<TaskResponseDto> getTasks(long userId);
    public UserDtoResponse updateUser(UserDto userDto,long userIId);
    public LoginResponseDto login(LoginRequestDto request);
    public List<DoctorResponseDto> getDoctor();
    public UserDtoResponse getUser(long id);
    public  List<TaskResponseDto> getAppointmentTasks(long appointmentId);
    public  List<TaskResponseDto> getAllTasks();
}
