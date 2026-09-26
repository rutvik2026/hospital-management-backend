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
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String serviceName;

    private Float serviceFee;

    private String description;

    private String result;

    @OneToMany(mappedBy = "services")
    private List<User> serviceUsers = new ArrayList<>();

    @OneToMany(mappedBy = "services")
    private List<Inventories> inventories=new ArrayList<>();

    @ManyToOne
    @JoinColumn()
    private Department department;

    public void addServiceUsers(User user){
        if(serviceUsers==null){
            serviceUsers=new ArrayList<>();
        }
        serviceUsers.add(user);
    }

    public void addInventory(Inventories inv){
        if(inventories==null){
            inventories=new ArrayList<>();
        }
        inventories.add(inv);
    }
}
