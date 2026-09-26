package com.hospital.system.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private float fee;

    private int stock;
    @ManyToOne
    private Services services;
    @OneToOne
    private Patient patient;
}
