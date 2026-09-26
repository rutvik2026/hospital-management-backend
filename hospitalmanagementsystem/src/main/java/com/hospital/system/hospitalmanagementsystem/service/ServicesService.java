package com.hospital.system.hospitalmanagementsystem.service;

import com.hospital.system.hospitalmanagementsystem.dto.*;
import com.hospital.system.hospitalmanagementsystem.entity.Services;

import java.util.List;

public interface ServicesService {
    public ServiceResponseDto addUser(long userId, long serviceId);
    public boolean removeUser(long userId,long serviceId);
    public InventoriesResponseDto addInventory(InventoriesRequestDto inventoriesRequestDto,long serviceId);
    public boolean removeInventory(long invId);
    public List<ServiceResponseDto> getServices(long departmentId);
    public InventoriesResponseDto addPatientToInventory(long inventoryId,long patientId);
    public InventoriesResponseDto updateInventoryStock(int newStock,long id);
    public InventoriesResponseDto updateInventory(InventoriesRequestDto inventoriesRequestDto,long id);
    public InventoriesResponseDto removePatientToInventory(long inventoryId);

    public List<InventoriesResponseDto> getServiceInventories(long serviceId);

    public List<ServiceUserResponse> getServiceUsers(long serviceId);
}
