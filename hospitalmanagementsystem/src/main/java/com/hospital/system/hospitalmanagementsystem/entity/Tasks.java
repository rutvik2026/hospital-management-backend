package com.hospital.system.hospitalmanagementsystem.entity;

import com.hospital.system.hospitalmanagementsystem.enums.TaskEnum;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String taskName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskEnum status;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services services;

    // Appointment for which this task was created
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    // Result entered by employee after completing task
    @Column(columnDefinition = "TEXT")
    private String result;
}