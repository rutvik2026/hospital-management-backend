package com.hospital.system.hospitalmanagementsystem.controller;

import com.hospital.system.hospitalmanagementsystem.dto.DepartmentRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.DepartmentResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceResponseDto;
import com.hospital.system.hospitalmanagementsystem.entity.Department;
import com.hospital.system.hospitalmanagementsystem.service.DepartmentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/admin/add/department")
    public ResponseEntity<DepartmentResponseDto> addDepartment(@RequestBody DepartmentRequestDto departmentRequestDto){
        DepartmentResponseDto response=departmentService.addDepartment(departmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("/admin/remove/dept/{id}")
    public ResponseEntity<Boolean> removeDepartment(@PathVariable long id){
        boolean response=departmentService.removeDepartment(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/any/get/dept")
    public ResponseEntity<List<DepartmentResponseDto>> getDepartments(){
        List<DepartmentResponseDto> response=departmentService.getDepartments();
        response.forEach(d->
                System.out.println(d.getDeptName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/admin/add/role/dept/{role}/{id}")
    public ResponseEntity<Boolean> addRoles(@PathVariable String role,@PathVariable long id){
        boolean response=departmentService.addRoles(role,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/admin/remove/role/dept//{role}/{id}")
    public ResponseEntity removeRoles(@PathVariable String role,@PathVariable long id){
        boolean response=departmentService.removeRoles(role,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admin/add/serv/dept/{id}")
    public ResponseEntity<ServiceResponseDto> addService(@RequestBody ServiceRequestDto serviceRequestDto,@PathVariable long id){
        ServiceResponseDto response=departmentService.addService(serviceRequestDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/admin/remove/serv/dept/{servicesId}/{id}")
    public ResponseEntity<Boolean> removeService(@PathVariable long servicesId,@PathVariable long id){
        Boolean response=departmentService.removeService(servicesId,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/admin/update/dept/{deptId}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@RequestBody DepartmentRequestDto departmentRequestDto,@PathVariable long deptId){
        DepartmentResponseDto response=departmentService.updateDepartment(departmentRequestDto,deptId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/admin/update/service/{serviceId}")
    public ResponseEntity<ServiceResponseDto> updateServices(@RequestBody ServiceRequestDto serviceRequestDto, @PathVariable long serviceId){
        ServiceResponseDto response=departmentService.updateServices(serviceRequestDto,serviceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
