package com.hospital.system.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String deptName;

    @OneToOne
    @JoinColumn(nullable = false)
    private User headOfDept;

    private List<String> deptRole;

//    @OneToMany(mappedBy = "department")
//    private List<Doctor> doctors=new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Services> services=new ArrayList<>();

    public void addServices(Services serv){
        if(services==null){
            services=new ArrayList<>();
        }
        services.add(serv);
    }
//    public void addDoctor(Doctor doctor){
//        if(doctors==null){
//            doctors=new ArrayList<>();
//        }
//        doctors.add(doctor);
//    }

}
