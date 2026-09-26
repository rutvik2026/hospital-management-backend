package com.hospital.system.hospitalmanagementsystem.entity;

import com.hospital.system.hospitalmanagementsystem.enums.Gender;
import com.hospital.system.hospitalmanagementsystem.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        name = "users",
        indexes = {
                @Index(columnList = "email")
        }

)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 255)
    private String name;

    private Integer age;

    private Integer weight;

    @Column(nullable = false,unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "password_hash",nullable = false)
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 20)
    private String phone;

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<Tasks> tasks = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = true)
    private Services services;
    @PrePersist
    protected void setCreatedAt(){
        LocalDateTime now=LocalDateTime.now();
        createdAt=now;

    }

    public void addTask(Tasks newTask){
        if(tasks==null){
            tasks=new ArrayList<>();
        }
        tasks.add(newTask);
    }

}
