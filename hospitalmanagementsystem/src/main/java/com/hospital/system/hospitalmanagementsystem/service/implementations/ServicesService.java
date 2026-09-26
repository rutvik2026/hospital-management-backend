package com.hospital.system.hospitalmanagementsystem.service.implementations;

import com.hospital.system.hospitalmanagementsystem.dto.*;
import com.hospital.system.hospitalmanagementsystem.entity.*;
import com.hospital.system.hospitalmanagementsystem.repository.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicesService implements com.hospital.system.hospitalmanagementsystem.service.ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public ServiceResponseDto addUser(long userId, long serviceId){
        Optional<Services> serv=serviceRepository.findById(serviceId);
        Optional<User> user=userRepository.findById(userId);

        if(serv.isEmpty()){
            throw  new RuntimeException("Invalide service id");
        }
        Services services=serv.get();
        services.addServiceUsers(user.get());
        user.get().setServices(services);
        //serviceRepository.save(services);
        return ServiceResponseDto.builder()
                .id(services.getId())
                .serviceFee(services.getServiceFee()).serviceName(services.getServiceName())
                .description(services.getDescription()).result(services.getResult()).build();
    }

    @Override
    @Transactional
    public boolean removeUser(long userId,long serviceId){
        Optional<Services> serv=serviceRepository.findById(serviceId);
        Optional<User> user=userRepository.findById(userId);

        if(serv.isEmpty()){
            throw  new RuntimeException("Invalide service id");
        }
        Services services=serv.get();
        services.getServiceUsers().removeIf(se->se.getId()==serviceId);
        user.get().setServices(null);
        //serviceRepository.save(services);
        return true;
    }

    @Override
    @Transactional
    public InventoriesResponseDto addInventory(InventoriesRequestDto inventoriesRequestDto,long serviceId){
        Inventories inventories=Inventories.builder().fee(inventoriesRequestDto.getFee())
                .stock(inventoriesRequestDto.getStock()).name(inventoriesRequestDto.getName())
                .services(inventoriesRequestDto.getServices()).build();

        Inventories inventories1=inventoryRepository.save(inventories);
        Optional<Services> services=serviceRepository.findById(serviceId);
        services.get().addInventory(inventories1);
        inventories.setServices(services.get());
        //serviceRepository.save(services.get());
        return InventoriesResponseDto.builder().id(inventories1.getId()).fee(inventories1.getFee())
                .stock(inventories1.getStock()).name(inventories1.getName())
                .patientId(inventories1.getPatient().getId()).build();
    }

    @Override
    public boolean removeInventory(long invId){
        Optional<Inventories> inv=inventoryRepository.findById(invId);
        Optional<Services> services=serviceRepository.findById(inv.get().getServices().getId());
        if(inv.isEmpty() || services.isEmpty()){
            throw  new RuntimeException("Invalide inventory id");
        }else {
            services.get().getInventories().removeIf(in->in.getId()==invId);
            //serviceRepository.save(services.get());
            inventoryRepository.deleteById(invId);
            return true;
        }

    }
    @Override
    public List<ServiceResponseDto> getServices(long deptmentId){
        Department department=departmentRepository.findById(deptmentId)
                .orElseThrow(()->new RuntimeException("Invalide dept id"));

        System.out.println("inside getService main");

        List<Services> services=department.getServices();
        List<ServiceResponseDto> serviceResponseDtos=new ArrayList<>();
        for (Services serv:services){
            ServiceResponseDto newServices=ServiceResponseDto.builder()
                    .serviceName(serv.getServiceName()).id(serv.getId())
                    .serviceFee(serv.getServiceFee()).description(serv.getDescription())
                    .result(serv.getResult()).build();
            serviceResponseDtos.add(newServices);
        }
        serviceResponseDtos.forEach(d->
                System.out.println("serviceName "+d.getServiceName())
                );
        return serviceResponseDtos;
    }
    @Override
    public InventoriesResponseDto addPatientToInventory(long inventoryId,long patientId){
        Optional<Patient> patient=patientRepository.findById(patientId);
        Optional<Inventories> inventories=inventoryRepository.findById(inventoryId);
        if(patient.isEmpty() || inventories.isEmpty()){
            throw  new RuntimeException("Invalide inventory id");
        }
        inventories.get().setPatient(patient.get());
        Inventories inventories1=inventoryRepository.save(inventories.get());
        return InventoriesResponseDto.builder().id(inventories1.getId()).fee(inventories1.getFee())
                .stock(inventories1.getStock()).name(inventories1.getName())
                .patientId(inventories1.getPatient().getId()).build();
    }

    @Override
    public InventoriesResponseDto updateInventoryStock(int newStock,long id){
        Optional<Inventories> inventories=inventoryRepository.findById(id);
        if(inventories.isEmpty()){
            throw  new RuntimeException("Invalide inventory id");
        }
        inventories.get().setStock(newStock);
        Inventories inventories1=inventoryRepository.save(inventories.get());
        return InventoriesResponseDto.builder().id(inventories1.getId()).fee(inventories1.getFee())
                .stock(inventories1.getStock()).name(inventories1.getName())
                .patientId(inventories1.getPatient().getId()).build();
    }

    @Override
    public InventoriesResponseDto updateInventory(InventoriesRequestDto inventoriesRequestDto,long id){
        Inventories inventories=inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("invalide inventory id"));

        if(inventoriesRequestDto.getServices()!=null){
            inventories.setServices(inventoriesRequestDto.getServices());
        }
        Integer stock=inventoriesRequestDto.getStock();
        if(stock!=null){
            inventories.setStock(inventoriesRequestDto.getStock());
        }
        if(inventoriesRequestDto.getName()!=null){
            inventories.setName(inventoriesRequestDto.getName());
        }
        Float fee=inventoriesRequestDto.getFee();
        if(fee!=null){
            inventories.setFee(inventoriesRequestDto.getFee());
        }
        if(inventoriesRequestDto.getPatient()!=null){
            inventories.setPatient(inventoriesRequestDto.getPatient());
        }
        Inventories updatedInv=inventoryRepository.save(inventories);
        return InventoriesResponseDto.builder()
                .id(updatedInv.getId()).name(updatedInv.getName()).fee(updatedInv.getFee())
                .stock(updatedInv.getStock()).patientId(updatedInv.getPatient().getId()).build();

    }
    @Override
    public InventoriesResponseDto removePatientToInventory(long inventoryId){
        Inventories inventories=inventoryRepository.findById(inventoryId)
                .orElseThrow(()->new RuntimeException("invalide inventory id"));
        inventories.setPatient(null);
        Inventories inventories1=inventoryRepository.save(inventories);
        return InventoriesResponseDto.builder().id(inventories1.getId()).fee(inventories1.getFee())
                .stock(inventories1.getStock()).name(inventories1.getName())
                .patientId(inventories1.getPatient().getId()).build();
    }


    @Override
    public List<InventoriesResponseDto> getServiceInventories(long serviceId){
        Services service = serviceRepository
                .findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid service id"
                        )
                );

        return service.getInventories()
                .stream()
                .map(inventory ->
                        InventoriesResponseDto.builder()
                                .id(inventory.getId())
                                .name(inventory.getName())
                                .fee(inventory.getFee())
                                .stock(inventory.getStock())
                                .patientId(inventory.getPatient() != null
                                        ? inventory.getPatient().getId()
                                        : null)
                                .build()
                )
                .toList();
    }

    @Override
    public List<ServiceUserResponse> getServiceUsers(long serviceId){
        Services service = serviceRepository
                .findById(serviceId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid service id"
                        )
                );

        return service.getServiceUsers()
                .stream()
                .map(user ->
                        ServiceUserResponse.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .username(user.getName())
                                .email(user.getEmail())
                                .build()
                )
                .toList();
    }
}
