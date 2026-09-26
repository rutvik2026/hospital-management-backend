package com.hospital.system.hospitalmanagementsystem.service.implementations;

import com.hospital.system.hospitalmanagementsystem.dto.DepartmentRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.DepartmentResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.ServiceResponseDto;
import com.hospital.system.hospitalmanagementsystem.entity.Department;
import com.hospital.system.hospitalmanagementsystem.entity.Doctor;
import com.hospital.system.hospitalmanagementsystem.entity.Services;
import com.hospital.system.hospitalmanagementsystem.repository.DepartmentRepository;
import com.hospital.system.hospitalmanagementsystem.repository.DoctorRepository;
import com.hospital.system.hospitalmanagementsystem.repository.ServiceRepository;
import com.hospital.system.hospitalmanagementsystem.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public DepartmentResponseDto addDepartment(DepartmentRequestDto departmentRequestDto){

        Department department=Department.builder()
                .deptName(departmentRequestDto.getDeptName())
                .headOfDept(departmentRequestDto.getHeadOfDept())
                .deptRole(departmentRequestDto.getDeptRole())
                .services(departmentRequestDto.getServices()).build();

            Department newDepartment=departmentRepository.save(department);

            return DepartmentResponseDto.builder()
                    .id(newDepartment.getId()).deptRole(newDepartment.getDeptRole())
                    .deptName(newDepartment.getDeptName()).headOfDept(newDepartment.getHeadOfDept()).build();
    }

    @Override
    @Transactional
    public boolean removeDepartment(long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invalid department id"));

        // Department still contains services
        if (department.getServices() != null &&
                !department.getServices().isEmpty()) {

            throw new RuntimeException(
                    "Cannot delete department. Remove all services from this department first."
            );
        }

        departmentRepository.delete(department);

        return true;
    }

    @Override
    public DepartmentResponseDto updateDepartment(DepartmentRequestDto departmentRequestDto,long deptId){
        Department department=departmentRepository.findById(deptId)
                .orElseThrow(()->new RuntimeException("Invalide dept Id"));

        if(departmentRequestDto.getDeptName()!=null){
            department.setDeptName(departmentRequestDto.getDeptName());
        }
        if(departmentRequestDto.getServices()!=null){
            department.setServices(departmentRequestDto.getServices());
        }
        if(departmentRequestDto.getHeadOfDept()!=null){
            department.setHeadOfDept(departmentRequestDto.getHeadOfDept());
        }
        if(departmentRequestDto.getDeptRole()!=null){
            department.setDeptRole(departmentRequestDto.getDeptRole());
        }
        Department d=departmentRepository.save(department);
        return DepartmentResponseDto.builder()
                .id(d.getId()).deptName(d.getDeptName()).headOfDept(d.getHeadOfDept()).deptRole(d.getDeptRole())
                .build();
    }

    @Override
    public List<DepartmentResponseDto> getDepartments(){
        List<Department> departments=departmentRepository.findAll();
        List<DepartmentResponseDto> departmentResponseDtos=new ArrayList<>();
        for (Department dept:departments){

            DepartmentResponseDto newDept=DepartmentResponseDto.builder()
                    .id(dept.getId()).deptName(dept.getDeptName())
                    .headOfDeptId(dept.getHeadOfDept().getId()).headOfDeptName(dept.getHeadOfDept().getName())
                    .deptRole(dept.getDeptRole()).serviceCount(dept.getServices().size()).build();
            departmentResponseDtos.add(newDept);
        }
        return departmentResponseDtos;
    }

//    public boolean addDoctor(long deptId,long doctorId){
//
//        Doctor doctor=doctorRepository.findById(doctorId)
//                .orElseThrow(()->new RuntimeException("invalide doctor id"));
//
//        Department department=departmentRepository.findById(deptId)
//                .orElseThrow(()->new RuntimeException("Invalide dept id"));
//
//        department.addDoctor(doctor);
//        doctor.setDepartment(department);
//
//        return true;
//
//    }
//
//    public boolean removeDoctor(long deptId,long doctorId){
//
//        Department department=departmentRepository.findById(deptId)
//                .orElseThrow(()->new RuntimeException("Invalide dept id"));
//        department.getDoctors().removeIf((d)->d.getId()==doctorId);
//        return true;
//    }
    @Override
    public boolean addRoles(String role,long id){
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isEmpty()){
            throw  new RuntimeException("Invalide department id");
        }else {
            Department dept=department.get();
            dept.getDeptRole().add(role);
            departmentRepository.save(dept);
        }
        return true;
    }

    @Override
    public boolean removeRoles(String role,long id){
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isEmpty()){
            throw  new RuntimeException("Invalide department id");
        }else {
            Department dept=department.get();
            dept.getDeptRole().remove(role);
            departmentRepository.save(dept);
        }
        return true;
    }

    @Override
    @Transactional
    public ServiceResponseDto addService(ServiceRequestDto serviceRequestDto, long id){
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isEmpty()){
            throw  new RuntimeException("Invalide department id");
        }else {
            Services services=Services.builder().serviceName(serviceRequestDto.getServiceName())
                    .serviceFee(serviceRequestDto.getServiceFee()).description(serviceRequestDto.getDescription())
                    .result(serviceRequestDto.getResult()).serviceUsers(serviceRequestDto.getServiceUsers()).inventories(serviceRequestDto.getInventories())
                    .department(department.get()).build();

            Services newServices=serviceRepository.save(services);
            Department dept=department.get();
            dept.addServices(newServices);
            //Department d=departmentRepository.save(dept);
            return ServiceResponseDto.builder()
                    .serviceName(newServices.getServiceName()).id(newServices.getId())
                    .serviceFee(newServices.getServiceFee()).description(newServices.getDescription())
                    .result(newServices.getResult()).build();
        }
    }

    @Override
    public boolean removeService(long servicesId, long id){
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isEmpty()){
            throw  new RuntimeException("Invalide department id");
        }else {
            serviceRepository.deleteById(servicesId);
            Department dept=department.get();
            dept.getServices().removeIf(ser->ser.getId()==servicesId);
            //Department d=departmentRepository.save(dept);
            return true;
        }
    }

    public ServiceResponseDto updateServices(ServiceRequestDto serviceRequestDto,long serviceId){
        Services services=serviceRepository.findById(serviceId).orElseThrow(()->new RuntimeException("invalide service id"));

        if(serviceRequestDto.getDepartment()!=null){
            services.setDepartment(serviceRequestDto.getDepartment());
        }
        if(serviceRequestDto.getServiceUsers()!=null){
            services.setServiceUsers(serviceRequestDto.getServiceUsers());
        }
        if(serviceRequestDto.getServiceName()!=null){
            services.setServiceName(serviceRequestDto.getServiceName());
        }
        Float fee=serviceRequestDto.getServiceFee();
        if(fee!=null){
            services.setServiceFee(serviceRequestDto.getServiceFee());
        }
        if(serviceRequestDto.getInventories()!=null){
            services.setInventories(serviceRequestDto.getInventories());
        }
        if(serviceRequestDto.getDescription()!=null){
            services.setDescription(serviceRequestDto.getDescription());
        }
        if(serviceRequestDto.getResult()!=null){
            services.setResult(serviceRequestDto.getResult());
        }
        Services updatedServices=serviceRepository.save(services);
        return ServiceResponseDto.builder()
                .serviceName(updatedServices.getServiceName()).id(updatedServices.getId())
                .serviceFee(updatedServices.getServiceFee()).description(updatedServices.getDescription())
                .result(updatedServices.getResult()).build();
    }

}
