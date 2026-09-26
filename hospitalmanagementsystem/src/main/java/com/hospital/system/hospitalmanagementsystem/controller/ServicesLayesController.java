package com.hospital.system.hospitalmanagementsystem.controller;

import com.hospital.system.hospitalmanagementsystem.dto.InventoriesRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.InventoriesResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceUserResponse;
import com.hospital.system.hospitalmanagementsystem.entity.Services;
import com.hospital.system.hospitalmanagementsystem.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServicesLayesController {

    private final ServicesService servicesService;

    @PatchMapping("/admin/add/user/serv/{userId}/{serviceId}")
    public ResponseEntity<ServiceResponseDto> addUser(@PathVariable long userId,@PathVariable long serviceId){
        ServiceResponseDto response=servicesService.addUser(userId,serviceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/admin/remove/user/service/{userId}/{serviceId}")
    public ResponseEntity<Boolean> removeUser(@PathVariable long userId,@PathVariable long serviceId){
        boolean response=servicesService.removeUser(userId,serviceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admin/add/inventory/{serviceId}")
    public ResponseEntity<InventoriesResponseDto> addInventory(@RequestBody InventoriesRequestDto inventoriesRequestDto,@PathVariable long serviceId){
        InventoriesResponseDto response=servicesService.addInventory(inventoriesRequestDto,serviceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("/admin/remove/inventory/serv/{invId}")
    public ResponseEntity<Boolean> removeInventory(@PathVariable long invId){
        boolean response=servicesService.removeInventory(invId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/any/get/dept/serv/{departmentId}")
    public ResponseEntity<List<ServiceResponseDto>> getServices(
            @PathVariable long departmentId) {
            System.out.println("inside getserviceController");
        List<ServiceResponseDto> response =
                servicesService.getServices(departmentId);
        response.forEach(d->
                System.out.println("respo "+d.getServiceName())
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/emp/add/patients/inve/{inventoryId}/{patientId}")
    public ResponseEntity<InventoriesResponseDto> addPatientToInventory(@PathVariable long inventoryId,@PathVariable long patientId){
        InventoriesResponseDto response=servicesService.addPatientToInventory(inventoryId,patientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/emp/update/inve/stock/{newStock}/{id}")
    public ResponseEntity<InventoriesResponseDto> updateInventoryStock(@PathVariable int newStock, @PathVariable long id){
        InventoriesResponseDto response=servicesService.updateInventoryStock(newStock,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/admin/update/inve/{id}")
    public ResponseEntity<InventoriesResponseDto> updateInventory(@RequestBody InventoriesRequestDto inventoriesRequestDto,@PathVariable long id){
        InventoriesResponseDto response=servicesService.updateInventory(inventoriesRequestDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/emp/remove/patients/inve/{inventoryId}")
    public ResponseEntity<InventoriesResponseDto> removePatientToInventory(@PathVariable long inventoryId){
        InventoriesResponseDto response=servicesService.removePatientToInventory(inventoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/any/get/inventory/serv/{serviceId}")
    public ResponseEntity<List<InventoriesResponseDto>> getServiceInventories(
            @PathVariable long serviceId) {

        return ResponseEntity.ok(
                servicesService.getServiceInventories(serviceId)
        );
    }

    @GetMapping("/any/get/user/serv/{serviceId}")
    public ResponseEntity<List<ServiceUserResponse>> getServiceUsers(
            @PathVariable long serviceId) {

        return ResponseEntity.ok(
                servicesService.getServiceUsers(serviceId)
        );
    }
}
