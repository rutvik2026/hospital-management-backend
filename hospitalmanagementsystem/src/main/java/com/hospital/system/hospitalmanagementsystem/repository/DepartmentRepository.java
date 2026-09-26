package com.hospital.system.hospitalmanagementsystem.repository;

import com.hospital.system.hospitalmanagementsystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

}
