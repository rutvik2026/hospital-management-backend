package com.hospital.system.hospitalmanagementsystem.entity;

import com.hospital.system.hospitalmanagementsystem.enums.AppointmentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;
    private LocalDate appointmentDate;

    private LocalTime appointmentTime;
    @OneToOne(mappedBy = "appointment")
    private Diagnosis diagnosis;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;

    @OneToMany(
            mappedBy = "appointment",
            cascade = CascadeType.ALL
    )
    private List<Tasks> tasks = new ArrayList<>();

    public void addTask(Tasks task) {

        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        tasks.add(task);
        task.setAppointment(this);
    }
}
