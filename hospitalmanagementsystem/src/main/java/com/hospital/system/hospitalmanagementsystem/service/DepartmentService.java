package com.hospital.system.hospitalmanagementsystem.service;

import com.hospital.system.hospitalmanagementsystem.dto.DepartmentRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.DepartmentResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceResponseDto;
import com.hospital.system.hospitalmanagementsystem.entity.Department;
import com.hospital.system.hospitalmanagementsystem.entity.Services;

import java.util.List;


public interface DepartmentService {
    public DepartmentResponseDto addDepartment(DepartmentRequestDto departmentRequestDto);
    public boolean removeDepartment(long id);
    public List<DepartmentResponseDto> getDepartments();
    public boolean addRoles(String role,long id);
    public boolean removeRoles(String role,long id);
    public ServiceResponseDto addService(ServiceRequestDto serviceRequestDto, long id);
    public boolean removeService(long servicesId, long id);
    public DepartmentResponseDto updateDepartment(DepartmentRequestDto departmentRequestDto,long deptId);
    public ServiceResponseDto updateServices(ServiceRequestDto serviceRequestDto, long serviceId);
}
