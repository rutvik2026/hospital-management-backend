package com.hospital.system.hospitalmanagementsystem.repository;

import com.hospital.system.hospitalmanagementsystem.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Services,Long> {
    void deleteByDepartmentId(long departmentId);
}
