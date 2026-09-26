package com.hospital.system.hospitalmanagementsystem.service.implementations;

import com.hospital.system.hospitalmanagementsystem.dto.AppointmentCreationDto;
import com.hospital.system.hospitalmanagementsystem.dto.AppointmentResponseDto;
import com.hospital.system.hospitalmanagementsystem.entity.Appointment;
import com.hospital.system.hospitalmanagementsystem.entity.Doctor;
import com.hospital.system.hospitalmanagementsystem.entity.Patient;
import com.hospital.system.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospital.system.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospital.system.hospitalmanagementsystem.repository.PatientRepository;
import com.hospital.system.hospitalmanagementsystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImplementation implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public AppointmentResponseDto createAppointment(AppointmentCreationDto appointmentCreationDto){
        System.out.println("appointDto"+appointmentCreationDto.toString());

        Doctor doctor = doctorRepository.findById(appointmentCreationDto.getDoctor().getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid doctor id: " +appointmentCreationDto.getDoctor().getId()
                        )
                );

        Patient patient = patientRepository.findByUser_Id(appointmentCreationDto.getPatient().getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "No patient found for user id: " + appointmentCreationDto.getPatient().getId()
                        )
                );
        System.out.println("Patient user id Doctor id"+appointmentCreationDto.getPatient().getId()+" "+appointmentCreationDto.getDoctor().getId());
        Appointment newAppointment=Appointment.builder()
                .appointmentType(appointmentCreationDto.getAppointmentType())
                .appointmentDate(appointmentCreationDto.getAppointmentDate())
                .appointmentTime(appointmentCreationDto.getAppointmentTime())
                .patient(patient)
                .doctor(doctor).build();

        Appointment appointment=appointmentRepository.save(newAppointment);


        doctor.addAppointment(appointment);

        patient.addAppointment(appointment);

        return AppointmentResponseDto.builder().id(appointment.getId()).appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime()).appointmentType(appointment.getAppointmentType()).doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getUser().getName())
                .patientId(appointment.getPatient().getId()).patientName(appointment.getPatient().getUser().getName()).build();

    }

    @Override
    public boolean removeAppointment(long id) {

        Optional<Appointment> appointmentOptional =
                appointmentRepository.findById(id);

        if (appointmentOptional.isEmpty()) {
            throw new RuntimeException(
                    "Appointment does not exist with this id"
            );
        }

        Appointment appointment = appointmentOptional.get();

        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();

        List<Appointment> appointments = doctor.getAppointments();

        appointments.removeIf(app -> app.getId()==id);
        doctorRepository.save(doctor);

        List<Appointment> patientAppointment=patient.getAppointments();
        patientAppointment.removeIf(app->app.getId()==id);
        patientRepository.save(patient);
        appointmentRepository.deleteById(id);

        return true;
    }
    @Override
    public List<AppointmentResponseDto> getAllAppointment(long userId, String role) {

        System.out.println(
                "User Id and role: " + userId + " " + role
        );

        if ("DOCTOR".equals(role)) {

            Doctor doctor = doctorRepository
                    .findByUser_Id(userId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Invalid doctor id: " + userId
                            )
                    );
            List<Appointment> appointments=doctor.getAppointments();

            List<AppointmentResponseDto> appointmentResponseDtos=new ArrayList<>();

            for(Appointment app:appointments){
                AppointmentResponseDto appointmentResponseDto=AppointmentResponseDto.builder()
                        .id(app.getId()).appointmentType(app.getAppointmentType())
                        .appointmentDate(app.getAppointmentDate()).appointmentTime(app.getAppointmentTime())
                        .doctorId(app.getDoctor().getId()).doctorName(app.getDoctor().getUser().getName())
                        .patientId(app.getId()).patientName(app.getPatient().getUser().getName()).specialization(app.getDoctor().getSpecialization()).build();
                appointmentResponseDtos.add(appointmentResponseDto);
            }
            return appointmentResponseDtos;

        } else if ("PATIENT".equals(role)) {

            Patient patient = patientRepository
                    .findByUser_Id(userId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Invalid patient id: " + userId
                            )
                    );

            List<Appointment> appointments=patient.getAppointments();
            List<AppointmentResponseDto> appointmentResponseDtos=new ArrayList<>();
            for(Appointment app:appointments){
                AppointmentResponseDto appointmentResponseDto=AppointmentResponseDto.builder()
                        .id(app.getId()).appointmentType(app.getAppointmentType())
                        .appointmentDate(app.getAppointmentDate()).appointmentTime(app.getAppointmentTime())
                        .doctorId(app.getDoctor().getId()).doctorName(app.getDoctor().getUser().getName())
                        .patientId(app.getId()).patientName(app.getPatient().getUser().getName())
                        .specialization(app.getDoctor().getSpecialization()).build();
                appointmentResponseDtos.add(appointmentResponseDto);
            }
            return appointmentResponseDtos;

        } else {

            throw new RuntimeException(
                    "Invalid role: " + role
            );
        }
    }

    @Override
    public AppointmentResponseDto updateAppointment(AppointmentCreationDto appointmentCreationDto,long appoinId){
        Appointment appointment=appointmentRepository.findById(appoinId)
                .orElseThrow(()->new RuntimeException("Invalide appointment Id"));

        if(appointmentCreationDto.getAppointmentDate()!=null){
            appointment.setAppointmentDate(appointmentCreationDto.getAppointmentDate());
        }
        if(appointmentCreationDto.getAppointmentTime()!=null){
            appointment.setAppointmentTime(appointmentCreationDto.getAppointmentTime());
        }
        if(appointmentCreationDto.getAppointmentType()!=null){
            appointment.setAppointmentType(appointmentCreationDto.getAppointmentType());
        }

        if(appointmentCreationDto.getDoctor()!=null){
            appointment.setDoctor(appointmentCreationDto.getDoctor());
        }
        Appointment updatedAppointment=appointmentRepository.save(appointment);
        return AppointmentResponseDto.builder()
                .id(updatedAppointment.getId()).appointmentTime(updatedAppointment.getAppointmentTime())
                .appointmentDate(updatedAppointment.getAppointmentDate()).doctorId(updatedAppointment.getDoctor().getId())
                .doctorName(updatedAppointment.getDoctor().getUser().getName())
                .patientId(updatedAppointment.getPatient().getId()).appointmentType(updatedAppointment.getAppointmentType())
                .patientName(updatedAppointment.getPatient().getUser().getName()).build();
    }
}
