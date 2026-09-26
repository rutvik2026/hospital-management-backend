package com.hospital.system.hospitalmanagementsystem.service.implementations;

import com.hospital.system.hospitalmanagementsystem.dto.*;
import com.hospital.system.hospitalmanagementsystem.entity.*;
import com.hospital.system.hospitalmanagementsystem.enums.Role;
import com.hospital.system.hospitalmanagementsystem.enums.TaskEnum;
import com.hospital.system.hospitalmanagementsystem.repository.*;
import com.hospital.system.hospitalmanagementsystem.security.JwtService;
import com.hospital.system.hospitalmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceInmplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    public UserServiceInmplementation(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public UserDtoResponse addUser(UserDto userDto){
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new RuntimeException("User Already exist");
        }

        User newUser=User.builder()
                        .name(userDto.getName())
                        .role(userDto.getRole()).age(userDto.getAge()).dateOfBirth(userDto.getDateOfBirth()).email(userDto.getEmail())
                        .gender(userDto.getGender()).phone(userDto.getPhone()).weight(userDto.getWeight())
                        .passwordHash(passwordEncoder.encode(userDto.getPassword())).build();
        User savedUser=userRepository.save(newUser);




        if(savedUser.getRole()== Role.PATIENT){

            Patient newPatient= Patient.builder().user(savedUser).build();
            patientRepository.save(newPatient);
        }else if(savedUser.getRole()== Role.DOCTOR){

            Doctor doctor=Doctor.builder().user(savedUser).build();
            doctor.setSpecialization(userDto.getSpecialization());
            doctorRepository.save(doctor);
        }

        return UserDtoResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .phone(savedUser.getPhone())
                .gender(savedUser.getGender())
                .build();
    }
    @Override
    public UserDtoResponse getUser(long id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("Invalide user id"));

        UserDtoResponse userDtoResponse=UserDtoResponse.builder()
                .id(user.getId()).name(user.getName()).email(user.getEmail()).role(user.getRole()).phone(user.getPhone())
                .gender(user.getGender())
                .age(user.getAge()).weight(user.getWeight()).dateOfBirth(user.getDateOfBirth()).build();
        System.out.println("user "+userDtoResponse);
        return userDtoResponse;
    }


    @Override
    @Transactional
    public TaskResponseDto addTasks(TaskRequestDto taskRequestDto) {

        Appointment appointment =
                appointmentRepository.findById(
                                taskRequestDto.getAppointmentId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Invalid appointment id")
                        );

        Services service =
                serviceRepository.findById(
                                taskRequestDto.getServiceId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Invalid service id")
                        );

        List<User> users =
                service.getServiceUsers();

        if (users == null || users.isEmpty()) {

            throw new RuntimeException(
                    "No employees are assigned to this service"
            );
        }

        /*
         * Find employee with lowest active task load
         */

        User selectedUser = null;

        long lowestLoad = Long.MAX_VALUE;

        for (User user : users) {

            long load =
                    taskRepository.countByUserIdAndStatusNot(
                            user.getId(),
                            TaskEnum.COMPLETED
                    );

            if (load < lowestLoad) {

                lowestLoad = load;
                selectedUser = user;
            }
        }

        if (selectedUser == null) {

            throw new RuntimeException(
                    "Unable to assign employee"
            );
        }

        Tasks newTask =
                Tasks.builder()
                        .taskName(taskRequestDto.getTaskName())
                        .user(selectedUser)
                        .services(service)
                        .appointment(appointment)
                        .status(
                                taskRequestDto.getStatus() != null
                                        ? taskRequestDto.getStatus()
                                        : TaskEnum.PENDING
                        )
                        .build();

        Tasks savedTask =
                taskRepository.save(newTask);

        appointment.addTask(savedTask);

        return TaskResponseDto.builder()
                .id(savedTask.getId())
                .taskName(savedTask.getTaskName())
                .userId(selectedUser.getId())
                .userName(selectedUser.getName())
                .serviceId(service.getId())
                .serviceName(service.getServiceName())
                .appointmentId(appointment.getId())
                .status(savedTask.getStatus())
                .result(savedTask.getResult())
                .build();
    }

    @Override
    public boolean removeTasks(long taskId, long userId){

        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("invalide user id"));
        Tasks task=taskRepository.findById(taskId).orElseThrow(()->new RuntimeException("invalide task id"));
        user.getTasks().removeIf(tasks->tasks.getId()==taskId);
        taskRepository.deleteAllById(Collections.singleton(taskId));

        return true;
    }
    @Override
    public boolean UpdateTaskByEmpl(TaskUpdateDto taskUpdateDto,long id){
        Tasks tasks=taskRepository.findById(id)
                .orElseThrow(()->new RuntimeException("invalide task id"));
        if (taskUpdateDto.getStatus() != null) {

            tasks.setStatus(
                    taskUpdateDto.getStatus()
            );
        }

        if (taskUpdateDto.getResult() != null) {

            tasks.setResult(
                    taskUpdateDto.getResult()
            );
        }

        taskRepository.save(tasks);
        return true;
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(
            TaskUpdateDto request,
            long taskId
    ) {

        Tasks task =
                taskRepository.findById(taskId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid task id"
                                )
                        );

        if (request.getStatus() != null) {

            task.setStatus(
                    request.getStatus()
            );
        }

        if (request.getResult() != null) {

            task.setResult(
                    request.getResult()
            );
        }

        Tasks updated =
                taskRepository.save(task);

        return TaskResponseDto.builder()
                .id(updated.getId())
                .taskName(updated.getTaskName())
                .userId(updated.getUser().getId())
                .userName(updated.getUser().getName())
                .serviceId(updated.getServices().getId())
                .serviceName(updated.getServices().getServiceName())
                .appointmentId(updated.getAppointment().getId())
                .status(updated.getStatus())
                .result(updated.getResult())
                .build();
    }

    public  List<TaskResponseDto> getAppointmentTasks(long appointmentId){
        {

            List<Tasks> tasks =
                    taskRepository.findByAppointmentId(
                            appointmentId
                    );

            List<TaskResponseDto> response =
                    tasks.stream()
                            .map(task ->
                                    TaskResponseDto.builder()
                                            .id(task.getId())
                                            .taskName(task.getTaskName())
                                            .userId(task.getUser().getId())
                                            .userName(task.getUser().getName())
                                            .serviceId(task.getServices().getId())
                                            .serviceName(
                                                    task.getServices().getServiceName()
                                            )
                                            .appointmentId(
                                                    task.getAppointment().getId()
                                            )
                                            .status(task.getStatus())
                                            .result(task.getResult())
                                            .build()
                            )
                            .toList();

            return response;
        }
    }
    @Override
    public List<TaskResponseDto> getTasks(long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("Invalide user id"));
        List<Tasks> tasks= user.getTasks();
        List<TaskResponseDto> taskResponseDtos=new ArrayList<>();
        for(Tasks t:tasks){
            TaskResponseDto newTaskResponseDto=TaskResponseDto.builder()
                    .id(t.getId()).taskName(t.getTaskName()).userId(t.getUser().getId())
                    .userName(t.getUser().getName()).serviceId(t.getServices().getId())
                    .serviceName(t.getServices().getServiceName()).appointmentId(t.getAppointment().getId())
                    .status(t.getStatus()).result(t.getResult()).build();
            taskResponseDtos.add(newTaskResponseDto);
        }
        return taskResponseDtos;
    }

    @Override
    public UserDtoResponse updateUser(UserDto userDto,long userIId){
        User user=userRepository.findById(userIId).orElseThrow(()->new RuntimeException("invalide user id"));
        if(userDto.getName()!=null){
            user.setName(userDto.getName());
        }

        if(userDto.getEmail()!=null){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getGender()!=null){
            user.setGender(userDto.getGender());
        }
        Integer age=userDto.getAge();
        if(age!=null){
            user.setAge(userDto.getAge());
        }
        if(userDto.getPhone()!=null){
            user.setPhone(userDto.getPhone());
        }
        Integer weight=userDto.getWeight();
        if(weight!=null){
            user.setWeight(userDto.getWeight());
        }
        if(userDto.getDateOfBirth()!=null){
            user.setDateOfBirth(userDto.getDateOfBirth());
        }
        User updatedUser=userRepository.save(user);
        return UserDtoResponse.builder()
                .id(updatedUser.getId()).name(updatedUser.getName()).email(updatedUser.getEmail())
                .role(updatedUser.getRole()).phone(updatedUser.getPhone())
                .gender(updatedUser.getGender()).build();
    }
    @Autowired
    private final JwtService jwtService;
    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid email or password"
                        ));

        boolean passwordMatch =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPasswordHash()
                );

        if (!passwordMatch) {
            throw new RuntimeException(
                    "Invalid email or password"
            );
        }


        String token = jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .token(token).userId(user.getId()).name(user.getName()).email(user.getEmail())
                .role(user.getRole()).build();
    }


    public List<DoctorResponseDto> getDoctor(){
        List<Doctor> doctors= doctorRepository.findAll();
        List<DoctorResponseDto> doctorResponseDtos=new ArrayList<>();
        for (Doctor doctor:doctors){

            User user=doctor.getUser();

            DoctorResponseDto doctorResponseDto = DoctorResponseDto.builder().id(doctor.getId()).name(user.getName()).email(user.getEmail())
                    .role(user.getRole()).phone(user.getPhone()).gender(user.getGender())
                    .specialization(doctor.getSpecialization()).build();
            doctorResponseDtos.add(doctorResponseDto);
        }
        return doctorResponseDtos;
    }

    @Override
    public  List<TaskResponseDto> getAllTasks(){
        List<Tasks> tasks=taskRepository.findAll();
        List<TaskResponseDto> taskResponseDtos=new ArrayList<>();
        for (Tasks t:tasks){
            TaskResponseDto taskResponseDto=TaskResponseDto.builder()
                    .id(t.getId()).taskName(t.getTaskName()).userId(t.getUser().getId())
                    .userName(t.getUser().getName()).serviceId(t.getServices().getId()).serviceName(t.getServices().getServiceName())
                    .appointmentId(t.getAppointment().getId()).status(t.getStatus()).result(t.getResult()).build();
            taskResponseDtos.add(taskResponseDto);
        }
        return taskResponseDtos;
    }
}
